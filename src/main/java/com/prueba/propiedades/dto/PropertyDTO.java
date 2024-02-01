package com.prueba.propiedades.dto;

import lombok.Data;

@Data
public class PropertyDTO {
    private String name;
    private String location;
    private boolean availability;
    private String imageUrl;
    private double price;
}
