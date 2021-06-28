package com.blaze.server.controllers;

import java.util.List;
import java.util.Map;

import com.blaze.server.models.Order;
import com.blaze.server.services.mongo.OrderService;

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
@RequestMapping("/api/order")
@CrossOrigin
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping
    public List<Order> GetOrders() {
        return orderService.getAll();
    }

    @GetMapping("/{id}")
    public Order GetOrder(@PathVariable String id) {
        return orderService.get(id);
    }

    @PostMapping
    public Order PostOrder(@RequestBody Order order) {
        return orderService.save(order);
    }

    @PutMapping
    public Order PutOrder(@RequestBody Order newOrder) {
        return orderService.update(newOrder);
    }

    @DeleteMapping("/{id}")
    public String DeleteOrder(@PathVariable String id) {
        orderService.delete(id);
        return id;
    }

    @GetMapping("/page")
    public Map<String, Object> GetOrdersPage(
        @RequestParam(name = "pageNo", defaultValue = "0") int pageNo,
        @RequestParam(name = "pageSize", defaultValue = "5") int size,
        @RequestParam(name = "sortBy", defaultValue = "id") String sortBy) {

        return orderService.getAllInPage(pageNo, size, sortBy);
    }
}
