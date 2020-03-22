package ru.geekbrains.supershop.services;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.geekbrains.supershop.exceptions.ProductNotFoundException;
import ru.geekbrains.supershop.persistence.entities.Product;
import ru.geekbrains.supershop.persistence.entities.enums.ProductCategory;
import ru.geekbrains.supershop.persistence.repositories.ProductRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product findOneById(UUID uuid) throws ProductNotFoundException {
        return productRepository.findById(uuid).orElseThrow(
            () -> new ProductNotFoundException("Oops! Product " + uuid + " wasn't found!")
        );
    }

    public List<Product> findProducts(Integer category, Boolean availability) {
        if (category == null && availability == null)
            return productRepository.findAll();

        if (category == null)
            return productRepository.findAllByAvailable(availability);

        if(availability == null)
            return productRepository.findAllByCategory(ProductCategory.values()[category]);

        return productRepository.findAllByCategoryAndAvailable(ProductCategory.values()[category], availability);
    }
}