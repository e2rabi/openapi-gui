package com.errabi.sandbox.repositories;

import com.errabi.sandbox.entities.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkspaceRepository extends JpaRepository<Workspace, Long>, QueryByExampleExecutor<Workspace> {
}
