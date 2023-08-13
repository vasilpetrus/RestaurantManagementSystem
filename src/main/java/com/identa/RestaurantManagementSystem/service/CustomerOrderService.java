package com.identa.RestaurantManagementSystem.service;

import com.identa.RestaurantManagementSystem.entity.CustomerOrder;
import com.identa.RestaurantManagementSystem.entity.Item;

import java.util.List;

public interface CustomerOrderService {
    CustomerOrder getCustomerOrderById(Long id);

    List<CustomerOrder> getCustomerOrders();

    CustomerOrder createNewOrder(List<Item> items, String customerName, String customerPhoneNumber, String deliveryAddress, String observation);

    void updateStatus(Long orderId, String newStatus);

    void deleteOrderById(Long orderId);
}
