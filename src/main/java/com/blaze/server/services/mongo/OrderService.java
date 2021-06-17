package com.blaze.server.services.mongo;

import java.util.List;

import com.blaze.server.models.Order;
import com.blaze.server.repositories.mongo.OrderRepository;
import com.blaze.server.services.interfaces.IOrderService;

import org.springframework.beans.factory.annotation.Autowired;
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
        Order oldUser = orderRepository.findById(item.getId()).orElse(null);
        oldUser.setStatus(item.getStatus());
        oldUser.setDate(item.getDate());
        oldUser.setCustomer(item.getCustomer());
        oldUser.setTaxes(item.getTaxes());
        oldUser.setTotalTaxes(item.getTotalTaxes());
        oldUser.setTotalAmount(item.getTotalAmount());
        oldUser.setOrderProducts(item.getOrderProducts());
        orderRepository.save(oldUser);
        
        return oldUser;
    }

    public void delete(String id) {
        orderRepository.deleteById(id);
    }
    
}