package com.example.auth.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.auth.models.Event;

@Repository
public interface EventRepository extends CrudRepository<Event, Long> {

}
