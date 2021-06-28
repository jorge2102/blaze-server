package com.blaze.server.services.mongo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.blaze.server.models.Order;
import com.blaze.server.repositories.mongo.OrderRepository;
import com.blaze.server.services.interfaces.IOrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class OrderService implements IOrderService {
    
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private IdentificationService identificationService;
    
    public List<Order> getAll() {
        return orderRepository.findAll();
    }
    
    public Order get(String id) {
        return orderRepository.findById(id).orElse(null);
    }

    public Order save(Order item) {
        int id = identificationService.getIdDocument(Order.class.getName());
        item.setId(String.valueOf(id));

        return orderRepository.save(item);
    }

    public Order update(Order item){
        Order order = orderRepository.findById(item.getId()).orElse(null);
        order.setStatus(item.getStatus());
        order.setDate(item.getDate());
        order.setCustomer(item.getCustomer());
        order.setTaxes(item.getTaxes());
        order.setTotalTaxes(item.getTotalTaxes());
        order.setTotalAmount(item.getTotalAmount());
        order.setOrderProducts(item.getOrderProducts());
        orderRepository.save(order);
        
        return order;
    }

    public void delete(String id) {
        orderRepository.deleteById(id);
    }
    
    public Map<String, Object> getAllInPage(int pageNo, int size, String sortBy) {
        Sort sort = Sort.by(sortBy);
        Pageable pageable = PageRequest.of(pageNo, size, sort);
        Page<Order> page = orderRepository.findAll(pageable);
        
        Map<String, Object> response = new HashMap<String, Object>();
        response.put("data", page.getContent());
        response.put("totalPage", page.getTotalPages());
        response.put("totalElement", page.getTotalElements());
        response.put("currentPage", page.getNumber());

        return response;
    }
}