package com.ppolivka.quittracker.service.impl;

import com.ppolivka.quittracker.dto.TrackerForm;
import com.ppolivka.quittracker.dto.User;
import com.ppolivka.quittracker.model.Tracker;
import com.ppolivka.quittracker.repository.TrackerRepository;
import com.ppolivka.quittracker.service.TrackerService;
import com.ppolivka.quittracker.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class TrackerServiceImpl implements TrackerService {

    @Value("${quit-tracker.date-format}")
    String formDateFormat = "d mmmm, yyyy";

    final TrackerRepository trackerRepository;
    final UserUtil userUtil;

    @Autowired
    public TrackerServiceImpl(TrackerRepository trackerRepository, UserUtil userUtil) {
        this.trackerRepository = trackerRepository;
        this.userUtil = userUtil;
    }

    @Override
    @Transactional
    @Secured("ROLE_USER")
    public void addNewTracker(TrackerForm form) {
        try {
            User user = userUtil.getUser().orElseThrow(RuntimeException::new);
            Tracker tracker = new Tracker();
            tracker.setOwner(user.getUserId());
            tracker.setCreatedDate(new Date());
            tracker.setName(form.getWho());
            tracker.setItem(form.getWhat());
            tracker.setSlug(form.getUrl());
            DateFormat dateFormat = new SimpleDateFormat(formDateFormat);
            tracker.setStopDate(dateFormat.parse(form.getWhen()));
            trackerRepository.save(tracker);
        } catch (ParseException e) {
            throw new RuntimeException("error adding tracker", e);
        }
    }

    @Override
    @Secured("ROLE_USER")
    public List<Tracker> listMyTrackers() {
        User user = userUtil.getUser().orElseThrow(RuntimeException::new);
        return trackerRepository.findByOwnerOrderByCreatedDateDesc(user.getUserId());
    }

    @Override
    public List<Tracker> listRecentTrackers() {
        return trackerRepository.findFirst5ByOrderByCreatedDateDesc();
    }

    @Override
    @Transactional
    public Tracker getTrackerByUrl(String url) {
        return trackerRepository.findBySlug(url);
    }
}
