package com.adidas.hackathon.events.dao;

import com.adidas.hackathon.events.domain.Stadium;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

@Repository
public interface StadiumsDAO extends JpaRepository<Stadium, Integer> {

    @RestResource(exported = false)
    @Override
    <S extends Stadium> S save(S s);
}
