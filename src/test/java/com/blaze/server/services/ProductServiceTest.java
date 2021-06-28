package com.blaze.server.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.blaze.server.models.Category;
import com.blaze.server.models.Product;
import com.blaze.server.repositories.mongo.ProductRepository;
import com.blaze.server.services.mongo.ProductService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class ProductServiceTest {
    
    @Autowired
    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;

    @Test
    void getAllTest() {
        when(productRepository.findAll())
            .thenReturn(Stream
                .of(new Product("1", "Chocolate", Category.Candies, 15, true),
                    new Product("2", "Mabels", Category.Cookies, 5, true))
                .collect(Collectors.toList()));

        assertEquals(2, productService.getAll().size());
    }
    
    @Test
    void getTest() {
        Product product = new Product("1", "Chocolate", Category.Candies, 15, true);
        
        when(productRepository.findById("1"))
            .thenReturn(Optional.of(product));

        assertEquals(product, productService.get("1"));
    }

    @Test
    public void saveTest() {
        Product product = new Product("1", "Chocolate", Category.Candies, 15, true);
        when(productRepository.save(product)).thenReturn(product);

        assertEquals(product, productService.save(product));
    }

    @Test
    public void updateTest() {
        Product productInitial = new Product("1", "Chocolate", Category.Candies, 15, true);
        Product product = new Product("1", "Chocolate", Category.Candies, 25, true);
        
        when(productRepository.findById("1")).thenReturn(Optional.of(productInitial));
        when(productRepository.save(product)).thenReturn(product);

        assertEquals(25, productService.update(product).getPrice());
    }

    @Test
    public void deleteTest() {
        Product product = new Product("1", "Chocolate", Category.Candies, 15, true);

        productService.delete(product.getId());
        verify(productRepository, times(1)).deleteById(product.getId());
    }
}
