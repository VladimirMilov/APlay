package com.adidas.hackathon.events.dao;

import com.adidas.hackathon.events.domain.NationalTeam;
import com.adidas.hackathon.events.domain.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource
public interface PlayersDAO extends JpaRepository<Player, Integer> {

    Player findByNationalTeamAndStartNumber(NationalTeam team, Integer startNumber);

    @RestResource(exported = false)
    @Override
    <S extends Player> S save(S s);
}
