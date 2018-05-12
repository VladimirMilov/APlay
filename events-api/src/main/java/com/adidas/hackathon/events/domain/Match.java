package com.adidas.hackathon.events.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity(name = "MATCH")
public class Match {

    @Id
    @Column(name = "IMATCHID")
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "IHOMEID", referencedColumnName = "INATIONALTEAMID")
    private NationalTeam home;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "IAWAYID", referencedColumnName = "INATIONALTEAMID")
    private NationalTeam away;

    @Column(name = "SDATE")
    private Date date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ISTADIUMID", referencedColumnName = "ISTADIUMID")
    private Stadium stadium;

}
