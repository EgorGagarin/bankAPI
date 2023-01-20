package com.gagarin.bankAPI.entity;

import javax.persistence.*;

@Entity
@Table(name = "roles")

public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ERole name;

    public Role() {
    }

    public Role(ERole name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ERole getName() {
        return name;
    }

    public void setName(ERole name) {
        this.name = name;
    }
}