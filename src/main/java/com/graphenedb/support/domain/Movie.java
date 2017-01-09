package com.graphenedb.support.domain;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.HashSet;
import java.util.Set;

@NodeEntity
public class Movie {

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @GraphId
    private Long id;
    private String title;
    private int released;

    @Relationship(type = "ACTS_IN", direction = "INCOMING")
    Set<Actor> actors = new HashSet<>();;

    public Movie() {
    }

    public Movie(String title, int year) {
        this.title = title;
        this.released = year;
    }

    public Set<Actor> getActors() {
        return actors;
    }

}