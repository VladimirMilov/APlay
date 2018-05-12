package com.adidas.hackathon.events.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity(name = "STADIUM")
public class Stadium {

    @Id
    @Column(name = "ISTADIUMID")
    private Integer id;

    @Column(name = "SNAME")
    private String name;

    @Column(name = "ICAPACITY")
    private Integer capacity;

    @Column(name = "FLATITUDE")
    private Float latitude;

    @Column(name = "FLONGITUDE")
    private Float longitude;

}
