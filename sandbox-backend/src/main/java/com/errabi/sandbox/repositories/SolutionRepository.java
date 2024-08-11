package com.errabi.sandbox.repositories;

import com.errabi.sandbox.entities.Solution;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolutionRepository extends JpaRepository<Solution, Long> {
    List<Solution> findSolutionsByReleaseId(Long releaseId);
    @Query("SELECT s FROM Solution s JOIN FETCH s.release")
    Page<Solution> findAllWithReleaseNames(Pageable pageable);
}
