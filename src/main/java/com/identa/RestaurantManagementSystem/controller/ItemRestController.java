package com.identa.RestaurantManagementSystem.controller;

import com.identa.RestaurantManagementSystem.entity.Item;
import com.identa.RestaurantManagementSystem.dto.ItemForm;
import com.identa.RestaurantManagementSystem.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This class defines a RESTful controller for managing items in the restaurant management system.
 * It handles various HTTP requests related to items, including retrieval, creation, and searching.
 * The controller delegates the business logic to the ItemService class for processing.
 *
 * Endpoints:
 * - GET /api/v1/item: Get a list of all items.
 * - POST /api/v1/item: Create a new item.
 * - GET /api/v1/item/some: Get a subset of items with pagination.
 * - GET /api/v1/item/search: Search for items based on a search term with pagination.
 */
@RestController
@RequestMapping("/api/v1/item")
public class ItemRestController {

    @Autowired
    private ItemService itemService;

    @GetMapping
    @CrossOrigin
    public List<Item> getAllItems() {
        return itemService.getAll();
    }

    @PostMapping
    @CrossOrigin
    public Item createItem(@RequestBody ItemForm form) {
        return itemService.createItem(new Item(
                form.getCode(),
                form.getName(),
                form.getDescription(),
                form.getImageUrl(),
                form.getPrice()
        ));
    }

    @GetMapping("some")
    @CrossOrigin
    public List<Item> getSomeItems(
            @RequestParam(name = "limit", required = false, defaultValue = "5") int limit,
            @RequestParam(name = "offset", required = false, defaultValue = "0") int offset) {
        return itemService.getSome(limit, offset);
    }

    @GetMapping("search")
    @CrossOrigin
    public List<Item> searchItems(
            @RequestParam(name = "term") String term,
            @RequestParam(name = "limit", required = false, defaultValue = "10") int limit,
            @RequestParam(name = "offset", required = false, defaultValue = "0") int offset) {
        return itemService.searchItems(term, limit, offset);
    }
}