package com.adidas.hackathon.events.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity(name = "NATIONALTEAM")
public class NationalTeam {

    @Id
    @Column(name = "INATIONALTEAMID")
    private Integer id;

    @Column(name = "SNAME")
    private String name;

    @Column(name = "IWINS")
    private Integer wins;

    @JsonIgnore
    @Column(name = "IATTACK")
    private Integer attack;

    @JsonIgnore
    @Column(name = "IDEFENSE")
    private Integer defense;

    @JsonIgnore
    @Column(name = "IAGGRESSIVE")
    private Integer aggressive;

    private String shortName;

    public String getShortName(){
        return name.replace(" ", "_").toLowerCase();
    }
}
