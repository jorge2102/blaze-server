package com.blaze.server.services.mongo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.blaze.server.models.Product;
import com.blaze.server.repositories.mongo.ProductRepository;
import com.blaze.server.services.interfaces.IProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class ProductService implements IProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private IdentificationService identificationService;
    
    public List<Product> getAll() {
        return productRepository.findAll();
    }
    
    public Product get(String id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product save(Product item) { 
        int id = identificationService.getIdDocument(Product.class.getSimpleName());
        item.setId(String.valueOf(id));

        return productRepository.save(item);
    }

    public Product update(Product item){
        Product oldUser = productRepository.findById(item.getId()).orElse(null);
        oldUser.setName(item.getName());
        oldUser.setCategory(item.getCategory());
        oldUser.setPrice(item.getPrice());
        oldUser.setActive(item.getActive());
        productRepository.save(oldUser);

        return oldUser;
    }

    public void delete(String id) {
        productRepository.deleteById(id);
    }
    
    public Map<String, Object> getAllInPage(int pageNo, int size, String sortBy) {
        Sort sort = Sort.by(sortBy);
        Pageable pageable = PageRequest.of(pageNo, size, sort);
        Page<Product> page = productRepository.findAll(pageable);
        
        Map<String, Object> response = new HashMap<String, Object>();
        response.put("data", page.getContent());
        response.put("totalPage", page.getTotalPages());
        response.put("totalElement", page.getTotalElements());
        response.put("currentPage", page.getNumber());

        return response;
    }
}
