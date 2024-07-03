package com.errabi.sandbox.repositories;

import com.errabi.sandbox.entities.Release;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReleaseRepository extends JpaRepository<Release, Long> {
    List<Release> findReleasesByProductId(Long productId);
}
