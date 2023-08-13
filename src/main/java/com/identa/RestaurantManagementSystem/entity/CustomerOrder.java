package com.identa.RestaurantManagementSystem.entity;

import com.identa.RestaurantManagementSystem.entity.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Item> items = new ArrayList<>();

    private String customerName;
    private String customerPhoneNumber;
    private String deliveryAddress;
    private String observation;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status = OrderStatus.PENDING;

    public CustomerOrder(List<Item> items, String customerName, String customerPhoneNumber, String deliveryAddress, String observation) {
        this.items = items;
        this.customerName = customerName;
        this.customerPhoneNumber = customerPhoneNumber;
        this.deliveryAddress = deliveryAddress;
        this.observation = observation;
    }
}
