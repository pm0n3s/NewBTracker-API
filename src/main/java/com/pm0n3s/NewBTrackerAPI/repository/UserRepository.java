package com.pm0n3s.NewBTrackerAPI.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.pm0n3s.NewBTrackerAPI.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{

}
