package com.identa.RestaurantManagementSystem.repository;

import com.identa.RestaurantManagementSystem.entity.Item;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("SELECT i FROM Item i")
    List<Item> getSome(Pageable pageable);

    @Query("SELECT i FROM Item i " +
            "WHERE UPPER(i.code) = UPPER(?1) " +
            "OR UPPER(i.name) LIKE UPPER(CONCAT('%',?1,'%'))")
    List<Item> searchItems(String searchTerm, Pageable pageable);

    List<Item> findByCodeIn(List<String> codes);
}