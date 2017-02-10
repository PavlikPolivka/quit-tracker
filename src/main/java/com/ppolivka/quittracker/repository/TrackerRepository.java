package com.ppolivka.quittracker.repository;

import com.ppolivka.quittracker.model.Tracker;
import org.springframework.data.repository.CrudRepository;


public interface TrackerRepository extends CrudRepository<Tracker, Integer> {

    Tracker findBySlug(String slug);

}
