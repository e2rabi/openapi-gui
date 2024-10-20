package com.errabi.openapi.repositories;

import com.errabi.openapi.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findProductsByWorkspaceId(Long workspaceId);

}
