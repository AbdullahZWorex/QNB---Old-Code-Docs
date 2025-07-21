package com.example.advancedsearchdemo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

import java.util.UUID;

@Entity
public class Subscription {
    @Id
    private Long id;
    private UUID uuid;

    @ManyToOne
    private Client client;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
