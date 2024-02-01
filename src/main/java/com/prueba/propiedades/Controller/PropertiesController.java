package com.prueba.propiedades.Controller;

import com.prueba.propiedades.Service.PropertyService;
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
    public ResponseEntity<List<PropertyConsultDTO>> searchProperties(@RequestBody ConsultPropertyDTO consultPropertyDTO) {
        List<PropertyConsultDTO> propertyList = propertyService.searchAvailableProperties(consultPropertyDTO);
        HttpStatus status = HttpStatus.OK;
        if (propertyList.isEmpty()) {
            status = HttpStatus.NO_CONTENT;
        }
        return new ResponseEntity<>(propertyList, status);
    }

    @PostMapping("/properties/register")
    public ResponseEntity<String> registerProperty(@RequestBody PropertyDTO propertyDTO) {
        try {
            propertyService.registerProperty(propertyDTO);
            return ResponseEntity.ok("Propiedad registrada exitosamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
