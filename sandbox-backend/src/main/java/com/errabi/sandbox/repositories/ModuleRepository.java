package com.errabi.sandbox.repositories;

import com.errabi.sandbox.entities.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ModuleRepository extends JpaRepository<Module, Long> {
    List<Module> findModulesBySolutionId(Long solutionId);
}
