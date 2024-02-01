package com.prueba.propiedades.dto;

import lombok.Data;

@Data
public class ConsultPropertyDTO {
    private boolean availability;
    private double minimalPrice;
    private double maximalPrice;
}
