package com.identa.RestaurantManagementSystem.controller;

import com.identa.RestaurantManagementSystem.entity.Item;
import com.identa.RestaurantManagementSystem.service.CustomerOrderService;
import com.identa.RestaurantManagementSystem.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;

/**
 * This class serves as a controller for managing the restaurant menu and placing customer orders.
 * It handles HTTP requests related to menu display and order placement.
 */
@Controller
public class MenuController {

    @Autowired
    private ItemService itemService;
    @Autowired
    CustomerOrderService customerOrderService;

    /**
     * Handles the HTTP GET request for displaying the restaurant menu.
     *
     * @param model The model used to pass data to the view.
     * @return The view name "menu" for rendering the menu page.
     */
    @GetMapping("/")
    public String showMenu(Model model) {
        List<Item> menuItems = itemService.getAll();
        model.addAttribute("menuItems", menuItems);
        return "menu";
    }

    /**
     * Handles the HTTP POST request for placing a customer order.
     *
     * @param selectedItems        A comma-separated list of selected item codes.
     * @param customerName         The name of the customer placing the order.
     * @param customerPhoneNumber  The phone number of the customer.
     * @param deliveryAddress      The delivery address for the order.
     * @param observation          Additional observations or notes for the order.
     * @return The view name "order_confirmation" for displaying the order confirmation page.
     */
    @PostMapping("/order")
    public String placeOrder(@RequestParam String selectedItems,
                             @RequestParam String customerName,
                             @RequestParam String customerPhoneNumber,
                             @RequestParam String deliveryAddress,
                             @RequestParam String observation) {
        List<String> selectedItemCodes = Arrays.asList(selectedItems.split(","));
        List<Item> selectedItemsList = itemService.getItemsByCodes(selectedItemCodes);
        customerOrderService.createNewOrder(
                selectedItemsList,
                customerName,
                customerPhoneNumber,
                deliveryAddress,
                observation
        );

        return "order_confirmation";
    }
}

