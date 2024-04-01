package com.dael.ApiRest.persistence.repository;

import com.dael.ApiRest.persistence.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
//    @Query("SELECT p FROM Product p WHERE p.price >= ?1 AND p.price <= ?2")
    @Query("SELECT p FROM Product p WHERE p.price BETWEEN ?1 AND ?2")
    List<Product> FindProductByPriceInRange (BigDecimal mixPrice, BigDecimal maxPrice) ;
    List<Product> findByPriceBetween (BigDecimal minPrice, BigDecimal maxPrice);
}
