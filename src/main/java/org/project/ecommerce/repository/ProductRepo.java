package org.project.ecommerce.repository;

import org.project.ecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface ProductRepo extends JpaRepository<Product, Integer> {
    List<Product> findAllByCategoryId(int id);
}
