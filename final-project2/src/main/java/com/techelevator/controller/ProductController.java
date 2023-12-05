package com.techelevator.controller;

import com.techelevator.dao.ProductDao;
import com.techelevator.exception.DaoException;
import com.techelevator.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@RestController
@RequestMapping(path = "/products")
@CrossOrigin
public class ProductController {

    @Autowired
    private ProductDao productDao;

    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<Product> list(@RequestParam(defaultValue = "") String name,
                              @RequestParam(defaultValue = "") String sku) {
        if (!sku.isEmpty() && !name.isEmpty()) {
            return productDao.getProductsByNameAndSku(name, sku);
        } else if(!sku.isEmpty()){
            return productDao.getProductsBySKU(sku);
        } else if(!name.isEmpty()){
            return productDao.getProductsByName(name);
        }
        return productDao.getProducts();
    }


    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public Product get(@PathVariable int id){
        try {
            Product product = productDao.getProductById(id);
            if (product != null) {
                return product;
            }
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found");
        } catch (DaoException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
