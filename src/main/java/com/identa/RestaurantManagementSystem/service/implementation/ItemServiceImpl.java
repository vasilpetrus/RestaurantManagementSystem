package com.identa.RestaurantManagementSystem.service.implementation;

import com.identa.RestaurantManagementSystem.entity.Item;
import com.identa.RestaurantManagementSystem.exception.ItemNotFoundException;
import com.identa.RestaurantManagementSystem.repository.ItemRepository;
import com.identa.RestaurantManagementSystem.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This class implements the ItemService interface to provide services related to managing items.
 * It handles item retrieval, creation, searching, and other related operations.
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    private final Logger log = LoggerFactory.getLogger(ItemServiceImpl.class);

    private static final String ITEM_NOT_FOUND = "Item '%s' not found";

    /**
     * Retrieves an item by its ID.
     *
     * @param id The ID of the item to retrieve.
     * @return The retrieved Item object.
     * @throws ItemNotFoundException if the item with the specified ID does not exist.
     */
    @Override
    public Item getItemById(Long id) {
        return itemRepository
                .findById(id)
                .orElseThrow(() -> new ItemNotFoundException(String.format(ITEM_NOT_FOUND, id)));
    }

    /**
     * Retrieves a list of all items.
     *
     * @return A list of Item objects representing all items.
     */
    @Override
    public List<Item> getAll() {
        return itemRepository.findAll();
    }

    /**
     * Retrieves a subset of items based on the provided limit and offset.
     *
     * @param limit  The maximum number of items to retrieve.
     * @param offset The offset for pagination.
     * @return A list of Item objects representing the requested subset of items.
     */
    @Override
    public List<Item> getSome(int limit, int offset) {
        return itemRepository.getSome(PageRequest.of(offset, limit));
    }

    /**
     * Searches for items based on a search term, limit, and offset.
     * Logs the search term for debugging purposes.
     *
     * @param searchTerm The search term for item search.
     * @param limit      The maximum number of items to retrieve.
     * @param offset     The offset for pagination.
     * @return A list of Item objects matching the search term.
     */
    @Override
    public List<Item> searchItems(String searchTerm, int limit, int offset) {
        log.info("searchTerm is {}", searchTerm);
        return itemRepository.searchItems(searchTerm, PageRequest.of(offset, limit));
    }

    /**
     * Creates a new item and saves it to the database.
     *
     * @param item The Item object to be created and saved.
     * @return The newly created Item object.
     */
    @Override
    public Item createItem(Item item) {
        return itemRepository.save(item);
    }

    /**
     * Retrieves a list of items based on a list of item codes.
     *
     * @param items The list of item codes.
     * @return A list of Item objects matching the provided item codes.
     */
    @Override
    public List<Item> getItemsByCodes(List<String> items) {
        return itemRepository.findByCodeIn(items);
    }
}