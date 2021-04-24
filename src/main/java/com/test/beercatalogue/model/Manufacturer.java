package com.test.beercatalogue.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

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
