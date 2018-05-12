package com.adidas.hackathon.events.dao;

import com.adidas.hackathon.events.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface EventsDAO extends JpaRepository<Event, Integer> {
}
