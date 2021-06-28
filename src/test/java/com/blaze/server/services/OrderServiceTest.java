package com.blaze.server.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.blaze.server.models.Order;
import com.blaze.server.models.Status;
import com.blaze.server.repositories.mongo.OrderRepository;
import com.blaze.server.services.mongo.OrderService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @MockBean
    private OrderRepository orderRepository;

    @Test
    void getAllTest() {
        when(orderRepository.findAll())
            .thenReturn(Stream
                .of(new Order("1", Status.Completed, new Date(), "John Lennon", 1.1, 1.1, 1.1, new ArrayList<>()),
                    new Order("2", Status.Completed, new Date(), "Jimi Hendrix", 1.1, 1.1, 1.1, new ArrayList<>()))
                .collect(Collectors.toList()));

        assertEquals(2, orderService.getAll().size());
    }
    
    @Test
    void getTest() {
        Order order = new Order("1", Status.Completed, new Date(), "John Lennon", 1.1, 1.1, 1.1, new ArrayList<>());
        
        when(orderRepository.findById("1"))
            .thenReturn(Optional.of(order));

        assertEquals(order, orderService.get("1"));
    }

    @Test
    public void saveTest() {
        Order order = new Order("1", Status.Completed, new Date(), "John Lennon", 1.1, 1.1, 1.1, new ArrayList<>());
        when(orderRepository.save(order)).thenReturn(order);

        assertEquals(order, orderService.save(order));
    }

    @Test
    public void updateTest() {
        Order orderInitial = new Order("1", Status.Completed, new Date(), "John Lennon", 1.1, 1.1, 1.1, new ArrayList<>());
        Order order = new Order("1", Status.Completed, new Date(), "John Lennon", 1.2, 1.2, 1.2, new ArrayList<>());
        
        when(orderRepository.findById("1")).thenReturn(Optional.of(orderInitial));
        when(orderRepository.save(order)).thenReturn(order);

        assertEquals(1.2, orderService.update(order).getTotalAmount());
    }

    @Test
    public void deleteTest() {
        Order order = new Order("1", Status.Completed, new Date(), "John Lennon", 1.1, 1.1, 1.1, new ArrayList<>());

        orderService.delete(order.getId());
        verify(orderRepository, times(1)).deleteById(order.getId());
    }
}
