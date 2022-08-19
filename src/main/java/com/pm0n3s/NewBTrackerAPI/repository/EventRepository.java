package com.pm0n3s.NewBTrackerAPI.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.pm0n3s.NewBTrackerAPI.model.Event;

@Repository
public interface EventRepository extends CrudRepository<Event, Long>{

}
