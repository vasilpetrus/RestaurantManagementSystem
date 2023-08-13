package com.identa.RestaurantManagementSystem.service;

import com.identa.RestaurantManagementSystem.entity.Item;

import java.util.List;

public interface ItemService {
    Item getItemById(Long id);

    List<Item> getAll();

    List<Item> getSome(int limit, int offset);

    List<Item> searchItems(String searchTerm, int limit, int offset);

    Item createItem(Item item);

    List<Item> getItemsByCodes(List<String> items);
}

