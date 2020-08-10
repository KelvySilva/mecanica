package br.com.sg.mechanical.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Vehicle extends AbstractEntity {


    private String brand;

    private String model;

    private String releaseDate;

    private String manufactureDate;

    private String licensePlate;

    @ManyToOne
    @JoinColumn(name = "client")
    private Client client;

}
