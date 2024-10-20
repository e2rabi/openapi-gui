package com.errabi.openapi.repositories;

import com.errabi.openapi.entities.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkspaceRepository extends JpaRepository<Workspace, Long>, QueryByExampleExecutor<Workspace> {
}
