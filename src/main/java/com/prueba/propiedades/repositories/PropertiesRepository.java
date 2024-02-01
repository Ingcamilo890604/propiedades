package com.prueba.propiedades.repositories;

import com.prueba.propiedades.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertiesRepository extends JpaRepository<Property, Long > {
    List<Property> findByAvailabilityAndPriceBetween(boolean Availability, double minimalPrice, double maximalPrice);

    boolean existsByName(String name);
}
