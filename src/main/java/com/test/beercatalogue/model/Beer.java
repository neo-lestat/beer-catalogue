package com.test.beercatalogue.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "beer",
        uniqueConstraints = { @UniqueConstraint(columnNames = {"id", "name"})})
public class Beer {
    @Id
    private int id;
    private String name;
    private float graduation;
    private String beerType;
    private String description;
    @ManyToOne
    @JoinColumn(name = "fk_manufacturer", nullable = false, updatable = false)
    private Manufacturer manufacturer;
}
