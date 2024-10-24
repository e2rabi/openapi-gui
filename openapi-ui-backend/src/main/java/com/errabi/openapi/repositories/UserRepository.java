package com.errabi.openapi.repositories;

import com.errabi.openapi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, QueryByExampleExecutor<User> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    Optional<User> findByUsername(String username);
    @Query("SELECT u FROM User u WHERE u.workspace.id = :workspaceId")
    List<User> findUsersByWorkspaceId(@Param("workspaceId") Long workspaceId);
    @Query("SELECT COUNT(u) FROM User u WHERE u.workspace.id = :workspaceId")
    long countUsersByWorkspaceId(@Param("workspaceId") Long workspaceId);
}
