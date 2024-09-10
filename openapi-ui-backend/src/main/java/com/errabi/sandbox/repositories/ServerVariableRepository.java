package com.errabi.sandbox.repositories;

import com.errabi.sandbox.entities.openapi.ServerVariable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServerVariableRepository extends JpaRepository<ServerVariable, Long> {
}
