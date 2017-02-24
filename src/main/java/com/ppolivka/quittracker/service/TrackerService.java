package com.ppolivka.quittracker.service;

import com.ppolivka.quittracker.dto.TrackerForm;
import com.ppolivka.quittracker.model.Tracker;

import java.util.List;

public interface TrackerService {

    void addNewTracker(TrackerForm form);

    List<Tracker> listMyTrackers();

    List<Tracker> listRecentTrackers();

    Tracker getTrackerByUrl(String url);

    void delete(Tracker tracker);

}
