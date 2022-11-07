package com.example.springboot.services;

import com.example.springboot.entities.Product;
import com.example.springboot.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private ProductRepository repository;

    @Autowired
    public void setRepository(ProductRepository repository) {
        this.repository = repository;
    }

    public List<Product> getAllProduct() {
        return repository.findAll();
    }

    public Product getById(Long id) {
        return repository.findById(id);
    }

    public void add(Product product) {
        repository.add(product);
    }

    public void update(Product product) {
        repository.update(product);
    }

    public void remove(Long id) {
        repository.remove(id);
    }
}
