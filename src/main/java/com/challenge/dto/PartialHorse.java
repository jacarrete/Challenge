package com.challenge.dto;

/**
 * Created by jcarretero on 15/03/2018.
 */
public class PartialHorse {

    private Long id;
    private String name;

    public PartialHorse() {
    }

    public PartialHorse(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
