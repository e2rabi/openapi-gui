package com.errabi.openapi.repositories;

import com.errabi.openapi.entities.openapi.Server;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServerRepository extends JpaRepository<Server,Long> {
}
