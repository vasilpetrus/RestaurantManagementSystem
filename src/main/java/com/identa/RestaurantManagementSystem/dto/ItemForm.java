package com.identa.RestaurantManagementSystem.dto;

import lombok.Data;

/**
 * This class represents a data transfer object (DTO) for capturing information about an item.
 * It is used to collect and transfer data related to item creation and modification.
 */
@Data
public class ItemForm {

    private String code;
    private String name;
    private String description;
    private String imageUrl;
    private Integer price;
}