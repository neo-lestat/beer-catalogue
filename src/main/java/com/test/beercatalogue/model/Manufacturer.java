package com.test.beercatalogue.model;

import lombok.Data;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Data
@Entity
@Table(name = "manufacturer",
        uniqueConstraints = { @UniqueConstraint(columnNames = {"name"})})
public class Manufacturer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @NotBlank(message = "Name is mandatory")
    private String name;
    @NotBlank(message = "Nationality is mandatory")
    private String nationality;
    //@OneToMany(cascade = CascadeType.ALL, mappedBy = "manufacturer")
    //private List<Beer> beers;
}
