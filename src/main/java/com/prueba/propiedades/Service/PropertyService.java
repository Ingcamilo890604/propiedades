package com.prueba.propiedades.Service;

import com.prueba.propiedades.Entity.Property;
import com.prueba.propiedades.General.exception.*;
import com.prueba.propiedades.Repositories.PropertiesRepository;
import com.prueba.propiedades.dto.ConsultPropertyDTO;
import com.prueba.propiedades.dto.PropertyConsultDTO;
import com.prueba.propiedades.dto.PropertyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class PropertyService {git add
    @Autowired
    private PropertiesRepository propertiesRepository;


    public List<PropertyConsultDTO> searchAvailableProperties(ConsultPropertyDTO consultPropertyDTO) {
        List<PropertyConsultDTO> result = new ArrayList<>();
        try {
            List<Property> propertiesList = propertiesRepository.findByAvailabilityAndPriceBetween(
                    consultPropertyDTO.isAvailability(),
                    consultPropertyDTO.getMinimalPrice(),
                    consultPropertyDTO.getMaximalPrice()
            );

            for (Property property : propertiesList) {
                PropertyConsultDTO propertyConsultDTO = new PropertyConsultDTO();
                propertyConsultDTO.setProperties(propertiesList);
                propertyConsultDTO.setMessage("la solicitud fué exitosa");
                result.add(propertyConsultDTO);
            }
        } catch (Exception e) {

        }
        return result;
    }
    public void registerProperty(PropertyDTO propertyDTO)  {

        if (StringUtils.isEmpty(propertyDTO.getName()) || StringUtils.isEmpty(propertyDTO.getLocation()) ||
                StringUtils.isEmpty(propertyDTO.getImageUrl()) || propertyDTO.getPrice() <= 0) {
            throw new EmptyFieldException("Todos los campos son obligatorios");
        }
        List<String> allowedLocations = Arrays.asList("Medellin", "Bogota", "Cali", "Cartagena");
        if (!allowedLocations.contains(propertyDTO.getLocation())) {
            throw new InvalidLocationException("Ubicación no permitida");
        }
        if (propertyDTO.getLocation().equals("Bogota") || propertyDTO.getLocation().equals("Cali")) {
            if (propertyDTO.getPrice() <= 2000000) {
                throw new InsufficientPriceException("El precio debe ser mayor a 2.000.000 para Bogotá y Cali");
            }
        } else {
            if (propertyDTO.getPrice() <= 0) {
                throw new InvalidPriceException("El precio debe ser mayor a 0");
            }
        }
        if (propertiesRepository.existsByName(propertyDTO.getName())) {
            throw new DuplicatePropertyException("Ya existe una propiedad con el mismo nombre");
        }

        Property property = new Property();
        property.setName(propertyDTO.getName());
        property.setLocation(propertyDTO.getLocation());
        property.setAvailability(propertyDTO.isAvailability());
        property.setUrl(propertyDTO.getImageUrl());
        property.setPrice(propertyDTO.getPrice());

        propertiesRepository.save(property);
    }

}
