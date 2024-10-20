package com.errabi.openapi.repositories;

import com.errabi.openapi.entities.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfigurationRepository extends JpaRepository<Configuration, Long>,
        PagingAndSortingRepository<Configuration, Long> {
    Optional<Configuration> findConfigurationByKey(String key);
}
