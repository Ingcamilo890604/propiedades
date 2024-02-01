package com.prueba.propiedades.Repositories;

import com.prueba.propiedades.Entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertiesRepository extends JpaRepository<Property, Long > {
    List<Property> findByAvailabilityAndPriceBetween(boolean Availability, double minimalPrice, double maximalPrice);

    boolean existsByName(String name);
}
