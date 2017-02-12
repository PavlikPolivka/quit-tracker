package com.ppolivka.quittracker.repository;

import com.ppolivka.quittracker.model.Tracker;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface TrackerRepository extends CrudRepository<Tracker, Integer> {

    Tracker findBySlug(String slug);

    List<Tracker> findFirst5ByOrderByCreatedDateDesc();

    List<Tracker> findByOwnerOrderByCreatedDateDesc(String owner);

}
