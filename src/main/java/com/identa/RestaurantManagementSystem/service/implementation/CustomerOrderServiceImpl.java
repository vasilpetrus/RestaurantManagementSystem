package com.identa.RestaurantManagementSystem.service.implementation;

import com.identa.RestaurantManagementSystem.entity.CustomerOrder;
import com.identa.RestaurantManagementSystem.entity.Item;
import com.identa.RestaurantManagementSystem.entity.enums.OrderStatus;
import com.identa.RestaurantManagementSystem.exception.CustomerOrderNotFoundException;
import com.identa.RestaurantManagementSystem.exception.StatusUpdateException;
import com.identa.RestaurantManagementSystem.repository.CustomerOrderRepository;
import com.identa.RestaurantManagementSystem.service.CustomerOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * This class implements the CustomerOrderService interface to provide services related to managing customer orders.
 * It handles order retrieval, creation, status updates, and deletion, including communication with WebSocket.
 */
@Service
@Transactional
public class CustomerOrderServiceImpl implements CustomerOrderService {

    @Autowired
    private CustomerOrderRepository orderRepository;

    @Autowired
    private ItemServiceImpl itemService;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    private static final String CUSTOMER_ORDER_NOT_FOUND = "Order '%s' not found";
    private static final String REDUNDANT_STATUS_UPDATE = "Cannot update order status to the same previous value '%s'";

    /**
     * Retrieves a customer order by its ID.
     *
     * @param id The ID of the order to retrieve.
     * @return The retrieved CustomerOrder object.
     * @throws CustomerOrderNotFoundException if the order with the specified ID does not exist.
     */
    @Override
    public CustomerOrder getCustomerOrderById(Long id) {
        return orderRepository
                .findById(id)
                .orElseThrow(() -> new CustomerOrderNotFoundException(
                        String.format(CUSTOMER_ORDER_NOT_FOUND, id)
                ));
    }

    /**
     * Retrieves a list of all customer orders, sorted by ID in ascending order.
     *
     * @return A list of CustomerOrder objects representing all orders.
     */
    @Override
    public List<CustomerOrder> getCustomerOrders() {
        List<CustomerOrder> orders = orderRepository.findAll();

        // sort by id in ascending order
        orders.sort((a, b) -> (int) (a.getId() - b.getId()));

        return orders;
    }

    /**
     * Creates a new customer order and saves it to the database.
     * Notifies WebSocket subscribers about the new order.
     *
     * @param items             List of items in the order.
     * @param customerName      Name of the customer placing the order.
     * @param customerPhoneNumber Phone number of the customer.
     * @param deliveryAddress   Address for order delivery.
     * @param observation       Additional notes or observations for the order.
     * @return The newly created CustomerOrder object.
     */
    @Override
    public CustomerOrder createNewOrder(String customerName, String customerPhoneNumber, String deliveryAddress, List<Item> items, String observation, Integer totalAmount) {
        CustomerOrder order = new CustomerOrder(items, customerName, customerPhoneNumber, deliveryAddress, observation, totalAmount);

        order = orderRepository.save(order);

        // Send update to WebSocket
        messagingTemplate.convertAndSend("/topic/order", order);

        return order;
    }

    /**
     * Updates the status of a customer order based on the provided new status.
     *
     * @param orderId   The ID of the order to update.
     * @param newStatus The new status to set for the order.
     * @throws StatusUpdateException if the provided new status is the same as the current status.
     */
    @Override
    public void updateStatus(Long orderId, String newStatus) {

        CustomerOrder order = getCustomerOrderById(orderId);

        OrderStatus status = switch (newStatus) {
            case ("PREPARING") -> OrderStatus.PREPARING;
            case ("IN_QUEUE") -> OrderStatus.IN_QUEUE;
            case ("AWAITING_DELIVERY") -> OrderStatus.AWAITING_DELIVERY;
            case ("DELIVERED") -> OrderStatus.DELIVERED;
            default -> OrderStatus.PENDING;
        };

        if (order.getStatus().toString().equals(status.toString())) {
            String exceptionMessage = String
                    .format(REDUNDANT_STATUS_UPDATE, status);
            throw new StatusUpdateException(exceptionMessage);
        }

        orderRepository.updateStatus(order.getId(), status);
    }

    /**
     * Deletes a customer order by its ID.
     *
     * @param orderId The ID of the order to delete.
     */
    @Override
    public void deleteOrderById(Long orderId) {
        orderRepository.deleteById(orderId);
    }
}
