package com.prueba.propiedades.service;

import com.prueba.propiedades.entity.Property;
import com.prueba.propiedades.general.constants.Constants;
import com.prueba.propiedades.general.enums.City;
import com.prueba.propiedades.general.exception.*;
import com.prueba.propiedades.repositories.PropertiesRepository;
import com.prueba.propiedades.dto.ConsultPropertyDTO;
import com.prueba.propiedades.dto.PropertyConsultDTO;
import com.prueba.propiedades.dto.PropertyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class PropertyService {
    @Autowired
    private PropertiesRepository propertiesRepository;


    public List<PropertyConsultDTO> searchAvailableProperties(ConsultPropertyDTO consultPropertyDTO) {
        List<PropertyConsultDTO> availableProperties = new ArrayList<>();
            List<Property> propertiesList = propertiesRepository.findByAvailabilityAndPriceBetween(
                    consultPropertyDTO.isAvailability(),
                    consultPropertyDTO.getMinimalPrice(),
                    consultPropertyDTO.getMaximalPrice()
            );
            PropertyConsultDTO propertyConsultDTO = new PropertyConsultDTO();
            propertyConsultDTO.setProperties(propertiesList);
            propertyConsultDTO.setMessage(Constants.SuccessfulRequest);

            availableProperties.add(propertyConsultDTO);

        return availableProperties;
    }
    public void registerProperty(PropertyDTO propertyDTO)  {

        if (ObjectUtils.isEmpty(propertyDTO.getName().toUpperCase()) || ObjectUtils.isEmpty(propertyDTO.getLocation().toUpperCase()) ||
                ObjectUtils.isEmpty(propertyDTO.getImageUrl()) ) {
            throw new EmptyFieldException(Constants.EmptyFieldExceptionMessage);
        }
        if (!isValidCity(propertyDTO.getLocation().toUpperCase())) {
            throw new InvalidLocationException(Constants.InvalidLocationExceptionMessage);
        }
        if (propertyDTO.getPrice() <= 0) {
            throw new InvalidPriceException(Constants.InvalidPriceExceptionMessage);
        }
        if (propertyDTO.getLocation().toUpperCase().equals(City.BOGOTA.toString()) || propertyDTO.getLocation().toUpperCase().equals(City.CALI.toString())) {
            if (propertyDTO.getPrice() <= 2000000) {
                throw new InsufficientPriceException(Constants.InsufficientPriceExceptionMessage);
            }
        }
        if (propertiesRepository.existsByName(propertyDTO.getName().toUpperCase())) {
            throw new DuplicatePropertyException(Constants.DuplicatePropertyExceptionMessage);
        }
        Property property = new Property();
        property.setName(propertyDTO.getName());
        property.setLocation(propertyDTO.getLocation());
        property.setAvailability(propertyDTO.isAvailability());
        property.setUrl(propertyDTO.getImageUrl());
        property.setPrice(propertyDTO.getPrice());
        propertiesRepository.save(property);
    }
    private boolean isValidCity(String location) {
        try {
            City.valueOf(location.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

}
