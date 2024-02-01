package com.prueba.propiedades.dto;

import com.prueba.propiedades.entity.Property;
import lombok.Data;

import java.util.List;
@Data
public class PropertyConsultDTO {
    private List<Property> properties;
    private String message;
}
