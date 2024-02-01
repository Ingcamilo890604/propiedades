package com.prueba.propiedades.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "nombre", unique = true)
    private String name;
    @Column(name = "ubicacion")
    private String location;
    @Column(name = "disponibilidad")
    private boolean availability;
    @Column(name = "url")
    private String url;
    @Column(name = "precio")
    private double price;
}
