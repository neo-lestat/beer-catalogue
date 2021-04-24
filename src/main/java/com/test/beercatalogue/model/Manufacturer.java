package com.test.beercatalogue.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "manufacturer",
        uniqueConstraints = { @UniqueConstraint(columnNames = {"id", "name"})})
public class Manufacturer {

    @Id
    private int id;
    private String name;
    @OneToOne(mappedBy = "manufacturer", cascade = CascadeType.ALL)
    private Nationality nationality;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "manufacturer")
    private List<Beer> beers;
}
