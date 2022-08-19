package com.pm0n3s.NewBTrackerAPI.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.pm0n3s.NewBTrackerAPI.model.EventType;

@Repository
public interface EventTypeRepository extends CrudRepository<EventType, Long> {

}
