package com.techelevator.dao;

import com.techelevator.model.Product;

import java.util.List;

public interface ProductDao {

    List<Product> getProducts();

    List<Product> getProductsByUserId(int userId);

    List<Product> getProductsBySKU(String productSKU);

    List<Product> getProductsByName(String name);

    Product getProductById(int productId);

    List<Product> getProductsByNameAndSku(String name, String sku);
}
