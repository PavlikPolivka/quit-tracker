package com.ppolivka.quittracker.controller;

import com.ppolivka.quittracker.exception.ResourceNotFoundException;
import com.ppolivka.quittracker.model.Tracker;
import com.ppolivka.quittracker.service.TrackerService;
import com.ppolivka.quittracker.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

import static java.time.LocalDateTime.now;
import static java.time.LocalDateTime.ofInstant;

@Controller
public class TrackerController {

    private final TrackerService trackerService;
    private final UserUtil userUtil;

    @Autowired
    public TrackerController(TrackerService trackerService, UserUtil userUtil) {
        this.trackerService = trackerService;
        this.userUtil = userUtil;
    }

    @RequestMapping(value = "/{trackerUrl:.+}", method = RequestMethod.GET)
    public String showTracker(@PathVariable("trackerUrl") String trackerUrl, Model model) {

        Tracker tracker = trackerService.getTrackerByUrl(trackerUrl);

        if (tracker == null) {
            throw new ResourceNotFoundException();
        }

        model.addAttribute("tracker", tracker);
        LocalDateTime stopped = ofInstant(tracker.getStopDate().toInstant(), ZoneId.systemDefault());
        LocalDateTime now = now();
        String diffString = diffDates(stopped, now);
        model.addAttribute("diff", diffString);
        return "tracker";
    }

    @RequestMapping(value = "/{trackerUrl}/time", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> getTrackerTime(@PathVariable("trackerUrl") String trackerUrl, Model model) {
        Tracker tracker = trackerService.getTrackerByUrl(trackerUrl);

        if (tracker == null) {
            throw new ResourceNotFoundException();
        }

        Map<String, String> json = new HashMap<>();
        LocalDateTime stopped = ofInstant(tracker.getStopDate().toInstant(), ZoneId.systemDefault());
        json.put("diff", diffDates(stopped, LocalDateTime.now()));
        return json;
    }

    @RequestMapping(value = "/{trackerUrl}/delete", method = RequestMethod.GET)
    @Secured("ROLE_USER")
    public String getTrackerTime(@PathVariable("trackerUrl") String trackerUrl) {
        Tracker tracker = trackerService.getTrackerByUrl(trackerUrl);

        if (tracker == null && !tracker.getOwner().equals(userUtil.getUser().get().getUserId())) {
            throw new ResourceNotFoundException();
        }

        trackerService.delete(tracker);

        return "redirect:/";
    }

    private String diffDates(LocalDateTime stopped, LocalDateTime now) {
        LocalDateTime tempDateTime = LocalDateTime.from(stopped);

        long years = tempDateTime.until(now, ChronoUnit.YEARS);
        tempDateTime = tempDateTime.plusYears(years);

        long months = tempDateTime.until(now, ChronoUnit.MONTHS);
        tempDateTime = tempDateTime.plusMonths(months);

        long days = tempDateTime.until(now, ChronoUnit.DAYS);
        tempDateTime = tempDateTime.plusDays(days);


        long hours = tempDateTime.until(now, ChronoUnit.HOURS);
        tempDateTime = tempDateTime.plusHours(hours);

        long minutes = tempDateTime.until(now, ChronoUnit.MINUTES);
        tempDateTime = tempDateTime.plusMinutes(minutes);

        StringBuilder date = new StringBuilder();
        addPart(years, date, " years ");
        addPart(months, date, " months ");
        addPart(days, date, " days ");
        addPart(hours, date, " hours ");
        addPart(minutes, date, " minutes ");

        return date.toString();
    }

    private void addPart(long months, StringBuilder date, String str) {
        if (months != 0) {
            date.append(months);
            date.append(str);
        }
    }


}
