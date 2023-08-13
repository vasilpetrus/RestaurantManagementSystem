package com.identa.RestaurantManagementSystem.service.implementation;

import com.identa.RestaurantManagementSystem.entity.CustomerOrder;
import com.identa.RestaurantManagementSystem.entity.Item;
import com.identa.RestaurantManagementSystem.entity.enums.OrderStatus;
import com.identa.RestaurantManagementSystem.exception.CustomerOrderNotFoundException;
import com.identa.RestaurantManagementSystem.exception.StatusUpdateException;
import com.identa.RestaurantManagementSystem.repository.CustomerOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CustomerOrderServiceImplTest {

    @Mock
    private CustomerOrderRepository orderRepository;

    @Mock
    private SimpMessagingTemplate messagingTemplate;

    @InjectMocks
    private CustomerOrderServiceImpl customerOrderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetCustomerOrderById() {
        CustomerOrder expectedOrder = new CustomerOrder();
        expectedOrder.setId(1L);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(expectedOrder));

        CustomerOrder actualOrder = customerOrderService.getCustomerOrderById(1L);

        assertNotNull(actualOrder);
        assertEquals(expectedOrder.getId(), actualOrder.getId());
    }

    @Test
    void testGetCustomerOrderByIdNotFound() {
        when(orderRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(CustomerOrderNotFoundException.class, () -> customerOrderService.getCustomerOrderById(1L));
    }

    @Test
    void testGetCustomerOrders() {
        CustomerOrder order1 = new CustomerOrder();
        order1.setId(1L);
        CustomerOrder order2 = new CustomerOrder();
        order2.setId(2L);

        when(orderRepository.findAll()).thenReturn(Arrays.asList(order1, order2));

        List<CustomerOrder> orders = customerOrderService.getCustomerOrders();

        assertEquals(2, orders.size());
        assertEquals(1L, orders.get(0).getId());
        assertEquals(2L, orders.get(1).getId());
    }

    @Test
    void testCreateNewOrder() {
        Item item = new Item();
        List<Item> items = Arrays.asList(item);
        String customerName = "John";
        String customerPhoneNumber = "1234567890";
        String deliveryAddress = "123 Main St";
        String observation = "Extra sauce";
        Integer totalAmount = 100;

        CustomerOrder expectedOrder = new CustomerOrder();

        when(orderRepository.save(any())).thenReturn(expectedOrder);

        ArgumentCaptor<String> topicCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Object> orderCaptor = ArgumentCaptor.forClass(Object.class);

        CustomerOrder actualOrder = customerOrderService.createNewOrder(customerName, customerPhoneNumber, deliveryAddress, items, observation, totalAmount);

        assertNotNull(actualOrder);

        verify(messagingTemplate, times(1)).convertAndSend(topicCaptor.capture(), orderCaptor.capture());

        assertEquals("/topic/order", topicCaptor.getValue());
        assertEquals(expectedOrder, orderCaptor.getValue());
    }

    @Test
    void testUpdateStatus() {
        CustomerOrder order = new CustomerOrder();
        order.setStatus(OrderStatus.PENDING);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        customerOrderService.updateStatus(1L, "IN_QUEUE"); // Changing to a different status

        CustomerOrder updatedOrder = customerOrderService.getCustomerOrderById(1L);

        assertEquals(OrderStatus.PENDING, updatedOrder.getStatus()); // Assert the correct updated status
    }

    @Test
    void testUpdateStatusSameStatus() {
        CustomerOrder order = new CustomerOrder();
        order.setStatus(OrderStatus.PREPARING);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        assertThrows(StatusUpdateException.class, () -> customerOrderService.updateStatus(1L, "PREPARING"));
    }

    @Test
    void testDeleteOrderById() {
        customerOrderService.deleteOrderById(1L);
        verify(orderRepository, times(1)).deleteById(1L);
    }
}