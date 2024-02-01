package com.prueba.propiedades.Repositories;

import com.prueba.propiedades.Entity.Properties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertiesRepository extends JpaRepository<Properties, Long > {
}
