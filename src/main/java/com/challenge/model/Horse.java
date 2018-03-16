package com.challenge.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by jcarretero on 15/03/2018.
 */
@Entity
public class Horse {

    @GeneratedValue
    @Id
    //@JsonIgnore
    private Long id;
    private String name;
    private String jockey;
    private String trainer;

    public Horse() {
    }

    public Horse(String name, String jockey, String trainer) {
        this.name = name;
        this.jockey = jockey;
        this.trainer = trainer;
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

    public String getJockey() {
        return jockey;
    }

    public void setJockey(String jockey) {
        this.jockey = jockey;
    }

    public String getTrainer() {
        return trainer;
    }

    public void setTrainer(String trainer) {
        this.trainer = trainer;
    }

    @Override
    public String toString() {
        return "Horse{" +
                "name='" + name + '\'' +
                ", jockey='" + jockey + '\'' +
                ", trainer='" + trainer + '\'' +
                '}';
    }
}
