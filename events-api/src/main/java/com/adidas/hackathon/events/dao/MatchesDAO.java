package com.adidas.hackathon.events.dao;

import com.adidas.hackathon.events.domain.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource
public interface MatchesDAO extends JpaRepository<Match, Integer> {

    @RestResource(exported = false)
    @Override
    <S extends Match> S save(S s);
}
