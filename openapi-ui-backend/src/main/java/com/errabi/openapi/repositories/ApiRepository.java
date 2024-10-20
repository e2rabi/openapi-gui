package com.errabi.openapi.repositories;

import com.errabi.openapi.entities.Api;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApiRepository extends JpaRepository<Api, Long> {
    List<Api> findApisByModuleId(Long moduleId);
}
