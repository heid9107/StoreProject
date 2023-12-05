package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Product;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcProductDao implements ProductDao{

    private final JdbcTemplate jdbcTemplate;

    public JdbcProductDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Product> getProducts() {
        List<Product> products = new ArrayList<>();

        try {
            String sql = "SELECT product_id, product_sku, name, description, price, image_name FROM product;";

            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                Product product = mapRowToProduct(results);
                products.add(product);
            }
        } catch(
                CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server or database", e);
        }
        return products;
    }

    @Override
    public List<Product> getProductsByUserId(int userId) {
        List<Product> products = new ArrayList<>();

        try {
            String sql = "SELECT p.product_id, product_sku, name, description, price, image_name FROM product p " +
                    "JOIN cart_item c ON c.product_id = p.product_id WHERE user_id = ?;";

            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
            while (results.next()) {
                Product product = mapRowToProduct(results);
                products.add(product);
            }
        } catch(
                CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server or database", e);
        }
        return products;
    }

    @Override
    public List<Product> getProductsBySKU(String productSKU) {
        List<Product> products = new ArrayList<>();

        try {
            String sql = "SELECT product_id, product_sku, name, description, price, image_name FROM product WHERE product_sku ILIKE ?;";

            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, "%"+ productSKU +"%");
            while (results.next()) {
                Product product = mapRowToProduct(results);
                products.add(product);
            }
        } catch(
                CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server or database", e);
        }
        return products;
    }



    @Override
    public List<Product> getProductsByName(String name) {
        List<Product> products = new ArrayList<>();

        try {
            String sql = "SELECT product_id, product_sku, name, description, price, image_name FROM product WHERE name ILIKE ?;";

            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, "%" + name + "%");
            while (results.next()) {
                Product product = mapRowToProduct(results);
                products.add(product);
            }
        } catch(
                CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server or database", e);
        }
        return products;
    }

    @Override
    public Product getProductById(int productId) {
        Product product = null;

        try {
            String sql = "SELECT product_id, product_sku, name, description, price, image_name FROM product WHERE product_id = ?;";

            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, productId);
            if (results.next()) {
                product = mapRowToProduct(results);
            }
        } catch(
                CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server or database", e);
        }
        return product;
    }

    @Override
    public List<Product> getProductsByNameAndSku(String name, String sku) {
        List<Product> products = new ArrayList<>();

        try {
            String sql = "SELECT product_id, product_sku, name, description, price, image_name FROM product WHERE name ILIKE ? AND product_sku ILIKE ?;";

            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, "%" + name + "%","%"+ sku + "%");
            while (results.next()) {
                Product product = mapRowToProduct(results);
                products.add(product);
            }
        } catch(
                CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server or database", e);
        }
        return products;
    }

    private Product mapRowToProduct(SqlRowSet result){
        Product product = new Product();
        product.setProductId(result.getInt("product_id"));
        product.setProductSKU(result.getString("product_sku"));
        product.setName(result.getString("name"));
        product.setDescription(result.getString("description"));
        product.setPrice(result.getBigDecimal("price"));
        product.setImageName(result.getString("image_name"));
        return product;
    }
}
