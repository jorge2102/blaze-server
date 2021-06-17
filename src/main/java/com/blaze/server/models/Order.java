package com.blaze.server.models;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    private String id;
    private Status status;
    private Date date;
    private String customer;
    private double taxes;
    private double totalTaxes;
    private double totalAmount;
    private List<OrderProduct> orderProducts;
    
}
