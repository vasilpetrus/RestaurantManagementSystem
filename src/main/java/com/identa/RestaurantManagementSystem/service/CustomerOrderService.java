package com.identa.RestaurantManagementSystem.service;

import com.identa.RestaurantManagementSystem.entity.CustomerOrder;
import com.identa.RestaurantManagementSystem.entity.Item;

import java.util.List;

public interface CustomerOrderService {
    CustomerOrder getCustomerOrderById(Long id);

    List<CustomerOrder> getCustomerOrders();

    CustomerOrder createNewOrder(String customerName, String customerPhoneNumber, String deliveryAddress, List<Item> items, String observation, Integer totalAmount);

    void updateStatus(Long orderId, String newStatus);

    void deleteOrderById(Long orderId);
}
