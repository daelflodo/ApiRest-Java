package com.dael.ApiRest.service.impl;

import com.dael.ApiRest.persistence.entities.Product;
import com.dael.ApiRest.persistence.IProductDAO;
import com.dael.ApiRest.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private IProductDAO productDAO;
    @Override
    public List<Product> findAll() {
        return productDAO.findAll();
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productDAO.findById(id);
    }

    @Override
    public List<Product> finByPriceInRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return productDAO.finByPriceInRange(minPrice, maxPrice);
    }

    @Override
    public void save(Product product) {
        productDAO.save(product);
    }

    @Override
    public void deleteById(Long id) {
        productDAO.deleteById(id);
    }
}
