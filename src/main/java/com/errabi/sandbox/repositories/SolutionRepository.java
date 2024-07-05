package com.errabi.sandbox.repositories;

import com.errabi.sandbox.entities.Solution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolutionRepository extends JpaRepository<Solution, Long> {
    List<Solution> findSolutionsByReleaseId(Long releaseId);
}
