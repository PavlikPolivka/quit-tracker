package com.ppolivka.quittracker.controller;

import com.ppolivka.quittracker.exception.ResourceNotFoundException;
import com.ppolivka.quittracker.model.Tracker;
import com.ppolivka.quittracker.service.TrackerService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public TrackerController(TrackerService trackerService) {
        this.trackerService = trackerService;
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

        long seconds = tempDateTime.until(now, ChronoUnit.SECONDS);

        return years + " years " + months + " months " + days + " days " + minutes + " minutes ";
    }


}
