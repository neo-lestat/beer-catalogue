package com.test.beercatalogue.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Entity
@Table(name = "beer",
        uniqueConstraints = { @UniqueConstraint(columnNames = {"name"})})
public class Beer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @NotBlank(message = "Name is mandatory")
    private String name;
    private Float graduation;
    private String beerType;
    private String description;
    @OneToOne
    @JoinColumn(name = "fk_manufacturer", nullable = false, updatable = false)
    private Manufacturer manufacturer;
}
