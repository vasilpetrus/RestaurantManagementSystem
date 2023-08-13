package com.identa.RestaurantManagementSystem.service.implementation;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.identa.RestaurantManagementSystem.entity.Item;
import com.identa.RestaurantManagementSystem.exception.ItemNotFoundException;
import com.identa.RestaurantManagementSystem.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ItemServiceImplTest {

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemServiceImpl itemService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetItemById_ItemExists_ReturnsItem() {
        // Arrange
        Item expectedItem = new Item(1L, "1004", "Coca-cola", "Refreshing cola - 0,33", "https://m.media-amazon.com/images/I/81nn0r6y-4L._AC_UF1000,1000_QL80_.jpg", 25);
        when(itemRepository.findById(1L)).thenReturn(Optional.of(expectedItem));

        // Act
        Item actualItem = itemService.getItemById(1L);

        // Assert
        assertEquals(expectedItem, actualItem);
    }

    @Test
    void testGetItemById_ItemNotExists_ThrowsItemNotFoundException() {
        // Arrange
        when(itemRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ItemNotFoundException.class, () -> itemService.getItemById(1L));
    }

    @Test
    void testGetAll_ReturnsListOfItems() {
        // Arrange
        List<Item> expectedItems = Arrays.asList(
                new Item("1005", "Coffee Americano", "Arabica 100%", "https://images.squarespace-cdn.com/content/v1/5a7cbe247131a5f17b3cc8fc/1519447742018-MOHBW2G0VOQ7QSCPJE14/Americano-Coffee-Lounge-Ingredients.jpg", 34),
                new Item("1003", "Cheese burger", "Perfect cheese burger", "https://yellowplate.ng/wp-content/uploads/2019/03/Cheese-Burger.jpg", 90)
        );
        when(itemRepository.findAll()).thenReturn(expectedItems);

        // Act
        List<Item> actualItems = itemService.getAll();

        // Assert
        assertEquals(expectedItems, actualItems);
    }

    @Test
    void testGetSome_ReturnsSubsetOfItems() {
        // Arrange
        List<Item> expectedItems = new ArrayList<>();
        when(itemRepository.getSome(PageRequest.of(0, 2))).thenReturn(expectedItems);

        // Act
        List<Item> actualItems = itemService.getSome(2, 0);

        // Assert
        assertEquals(expectedItems, actualItems);
    }

    @Test
    void testSearchItems_ReturnsListOfItemsMatchingSearchTerm() {
        // Arrange
        String searchTerm = "cola";
        List<Item> expectedItems = new ArrayList<>();
        when(itemRepository.searchItems(searchTerm, PageRequest.of(0, 2))).thenReturn(expectedItems);

        // Act
        List<Item> actualItems = itemService.searchItems(searchTerm, 2, 0);

        // Assert
        assertEquals(expectedItems, actualItems);
    }

    @Test
    void testCreateItem_ReturnsCreatedItem() {
        // Arrange
        Item newItem = new Item("1006", "Tea", "Black tea", "https://example.com/tea.jpg", 10);
        when(itemRepository.save(newItem)).thenReturn(newItem);

        // Act
        Item createdItem = itemService.createItem(newItem);

        // Assert
        assertEquals(newItem, createdItem);
    }

    @Test
    void testGetItemsByCodes_ReturnsListOfItemsMatchingCodes() {
        // Arrange
        List<String> itemCodes = Arrays.asList("1004", "1005");
        List<Item> expectedItems = new ArrayList<>();
        when(itemRepository.findByCodeIn(itemCodes)).thenReturn(expectedItems);

        // Act
        List<Item> actualItems = itemService.getItemsByCodes(itemCodes);

        // Assert
        assertEquals(expectedItems, actualItems);
    }
}
