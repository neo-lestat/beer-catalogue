package com.test.beercatalogue.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "nationality",
        uniqueConstraints = { @UniqueConstraint(columnNames = {"id", "country"})})
public class Nationality {

    @Id
    private int id;
    private String country;
    private String description;

    @OneToOne
    @JoinColumn(name = "fk_manufacturer", updatable = false, nullable = false)
    private Manufacturer manufacturer;
}
