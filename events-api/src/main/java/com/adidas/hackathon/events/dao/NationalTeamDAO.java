package com.adidas.hackathon.events.dao;

import com.adidas.hackathon.events.domain.NationalTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(path = "national-teams")
public interface NationalTeamDAO extends JpaRepository<NationalTeam, Integer> {

    @RestResource(exported = false)
    @Override
    <S extends NationalTeam> S save(S s);
}
