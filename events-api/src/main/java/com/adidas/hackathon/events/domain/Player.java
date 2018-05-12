package com.adidas.hackathon.events.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity(name = "PLAYER")
public class Player {

    @Id
    @GeneratedValue
    @Column(name = "IPLAYERID")
    private Integer id;

    @Column(name = "SFIRSTNAME")
    private String firstName;

    @Column(name = "SLASTNAME")
    private String lastName;

    @Column(name = "ISTARTNUMBER")
    private Integer startNumber;

    @RestResource(path = "national-teams")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "INATIONALTEAMID", referencedColumnName = "INATIONALTEAMID")
    private NationalTeam nationalTeam;

}
