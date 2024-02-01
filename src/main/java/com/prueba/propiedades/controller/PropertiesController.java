package com.prueba.propiedades.controller;

import com.prueba.propiedades.general.constants.Constants;
import com.prueba.propiedades.general.exception.*;
import com.prueba.propiedades.service.PropertyService;
import com.prueba.propiedades.dto.ConsultPropertyDTO;
import com.prueba.propiedades.dto.PropertyConsultDTO;
import com.prueba.propiedades.dto.PropertyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PropertiesController {
    @Autowired
    private PropertyService propertyService;
    @GetMapping("/searchProperties")
    public ResponseEntity<?> searchProperties(@RequestBody ConsultPropertyDTO consultPropertyDTO) {
        try {
            List<PropertyConsultDTO> propertyList = propertyService.searchAvailableProperties(consultPropertyDTO);
            HttpStatus status = propertyList.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
            return new ResponseEntity<>(propertyList, status);
        } catch (Exception e) {
            String errorMessage = Constants.FailSearchProperties + e.getMessage();
            PropertyConsultDTO errorResponse = new PropertyConsultDTO();
            errorResponse.setMessage(Constants.FailedRequest);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }

    @PostMapping("/properties/register")
    public ResponseEntity<String> registerProperty(@RequestBody PropertyDTO propertyDTO) {
        try {
            propertyService.registerProperty(propertyDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(Constants.SuccessfulRegister);
        } catch (EmptyFieldException | InvalidLocationException | InvalidPriceException |
                 InsufficientPriceException | DuplicatePropertyException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Constants.FailedRequest);
        }
    }
}
