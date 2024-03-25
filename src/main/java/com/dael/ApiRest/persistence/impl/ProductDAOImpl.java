package com.dael.ApiRest.persistence.impl;

import com.dael.ApiRest.entities.Product;
import com.dael.ApiRest.persistence.IProductDAO;
import com.dael.ApiRest.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Component
public class ProductDAOImpl implements IProductDAO {

    @Autowired
    private ProductRepository productRepository;
    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public List<Product> finByPriceInRange(BigDecimal minPrice, BigDecimal maxPrice) {
//        return productRepository.FindProductByPricesBetween(minPrice, maxPrice);
        return productRepository.FindProductByPriceInRange(minPrice, maxPrice);
    }

    @Override
    public void save(Product product) {
        productRepository.save(product);
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}
