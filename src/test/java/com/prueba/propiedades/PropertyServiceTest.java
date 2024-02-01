package com.prueba.propiedades;

import com.prueba.propiedades.entity.Property;
import com.prueba.propiedades.general.constants.Constants;
import com.prueba.propiedades.general.exception.*;
import com.prueba.propiedades.repositories.PropertiesRepository;
import com.prueba.propiedades.service.PropertyService;
import com.prueba.propiedades.dto.ConsultPropertyDTO;
import com.prueba.propiedades.dto.PropertyConsultDTO;
import com.prueba.propiedades.dto.PropertyDTO;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PropertyServiceTest {

    @Mock
    private PropertiesRepository propertiesRepository;

    @InjectMocks
    private PropertyService propertyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSearchAvailablePropertiesSuccessful() {

        ConsultPropertyDTO consultPropertyDTO = new ConsultPropertyDTO();
        consultPropertyDTO.setAvailability(true);
        consultPropertyDTO.setMinimalPrice(1000000);
        consultPropertyDTO.setMaximalPrice(2000000);

        List<Property> mockPropertiesList = new ArrayList<>();
        Property property = new Property();

        mockPropertiesList.add(property);

        when(propertiesRepository.findByAvailabilityAndPriceBetween(true, 1000000, 2000000))
                .thenReturn(mockPropertiesList);


        List<PropertyConsultDTO> result = propertyService.searchAvailableProperties(consultPropertyDTO);


        assertFalse(result.isEmpty());
        assertEquals(Constants.SuccessfulRequest, result.get(0).getMessage());
        assertEquals(mockPropertiesList, result.get(0).getProperties());
    }

    @Test
    public void testSearchAvailablePropertiesWhenRepositoryFail(){
        ConsultPropertyDTO consultPropertyDTO = new ConsultPropertyDTO();
        consultPropertyDTO.setAvailability(true);
        consultPropertyDTO.setMinimalPrice(1000000);
        consultPropertyDTO.setMaximalPrice(2000000);

        when(propertiesRepository.findByAvailabilityAndPriceBetween(true, 1000000, 2000000))
                .thenThrow(new RuntimeException("Error al buscar propiedades"));


        List<PropertyConsultDTO> result = propertyService.searchAvailableProperties(consultPropertyDTO);

        assertEquals(Constants.FailedRequest, result.get(0).getMessage());
    }

    @Test
    public void testRegisterPropertySuccessful() {

        PropertyDTO propertyDTO = new PropertyDTO();
        propertyDTO.setName("Test Property");
        propertyDTO.setLocation("Bogota");
        propertyDTO.setAvailability(true);
        propertyDTO.setImageUrl("https://example.com/image.jpg");
        propertyDTO.setPrice(3000000);
        assertDoesNotThrow(() -> propertyService.registerProperty(propertyDTO));
    }
    @Test
    public void testRegisterPropertyWhenAnyFieldIsEmpty() {

        PropertyDTO propertyDTO = new PropertyDTO();
        propertyDTO.setName("Test Property");
        propertyDTO.setLocation("Bogota");
        propertyDTO.setAvailability(true);
        propertyDTO.setImageUrl("https://example.com/image.jpg");
        propertyDTO.setPrice(3000000);
        assertDoesNotThrow(() -> propertyService.registerProperty(propertyDTO));
    }
    @Test
    public void testRegisterPropertyWhenEmptyName() {
        PropertyDTO propertyDTO = new PropertyDTO();
        propertyDTO.setName("");
        propertyDTO.setLocation("Bogota");
        propertyDTO.setAvailability(true);
        propertyDTO.setImageUrl("https://example.com/image.jpg");
        propertyDTO.setPrice(3000000);
        assertThrows(EmptyFieldException.class, () -> propertyService.registerProperty(propertyDTO));

    }

    @Test
    public void testRegisterPropertyWhenCityIsNotAuthorized() {
        PropertyDTO propertyDTO = new PropertyDTO();
        propertyDTO.setName("Hacienda Santa Ana");
        propertyDTO.setLocation("Leticia");
        propertyDTO.setAvailability(true);
        propertyDTO.setImageUrl("https://example.com/image.jpg");
        propertyDTO.setPrice(3000000);
        assertThrows(InvalidLocationException.class, () -> propertyService.registerProperty(propertyDTO));
    }

    @Test
    public void testRegisterPropertyWhenPriceIsInsufficient() {
        PropertyDTO propertyDTO = new PropertyDTO();
        propertyDTO.setName("Hacienda Santa Ana");
        propertyDTO.setLocation("Bogota");
        propertyDTO.setAvailability(true);
        propertyDTO.setImageUrl("https://example.com/image.jpg");
        propertyDTO.setPrice(30);
        assertThrows(InsufficientPriceException.class, () -> propertyService.registerProperty(propertyDTO));
    }
    @Test
    public void testRegisterPropertyWhenPriceIsInvalid() {
        PropertyDTO propertyDTO = new PropertyDTO();
        propertyDTO.setName("Hacienda Santa Ana");
        propertyDTO.setLocation("Medellin");
        propertyDTO.setAvailability(true);
        propertyDTO.setImageUrl("https://example.com/image.jpg");
        propertyDTO.setPrice(-3);
        assertThrows(InvalidPriceException.class, () -> propertyService.registerProperty(propertyDTO));
    }

    @Test
    public void testRegisterPropertyWhenNameExist() {
        PropertyDTO propertyDTO = new PropertyDTO();
        propertyDTO.setName("Hacienda Santa Ana");
        propertyDTO.setLocation("Medellin");
        propertyDTO.setAvailability(true);
        propertyDTO.setImageUrl("https://example.com/image.jpg");
        propertyDTO.setPrice(20000000);
        when(propertiesRepository.existsByName(any())).thenReturn(true);
        assertThrows(DuplicatePropertyException.class, () -> propertyService.registerProperty(propertyDTO));
    }
}
