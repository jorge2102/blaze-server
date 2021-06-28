package com.blaze.server.controllers;

import java.util.List;
import java.util.Map;

import com.blaze.server.models.Product;
import com.blaze.server.services.mongo.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/product")
@CrossOrigin
public class ProductController {
    
    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> GetProducts() {
        return productService.getAll();
    }

    @GetMapping("/{id}")
    public Product GetProduct(@PathVariable String id) {
        return productService.get(id);
    }

    @PostMapping
    public Product PostProduct(@RequestBody Product product) {
        return productService.save(product);
    }

    @PutMapping
    public Product PutProduct(@RequestBody Product newProduct) {
        return productService.update(newProduct);
    }

    @DeleteMapping("/{id}")
    public String DeleteProduct(@PathVariable String id) {
        productService.delete(id);
        return id;
    }

    @GetMapping("/page")
    public Map<String, Object> GetOrdersPage(
        @RequestParam(name = "pageNo", defaultValue = "0") int pageNo,
        @RequestParam(name = "pageSize", defaultValue = "5") int size,
        @RequestParam(name = "sortBy", defaultValue = "id") String sortBy) {

        return productService.getAllInPage(pageNo, size, sortBy);
    }
}
