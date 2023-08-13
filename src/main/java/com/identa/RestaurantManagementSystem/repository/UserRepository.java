package com.identa.RestaurantManagementSystem.repository;

import com.identa.RestaurantManagementSystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByLogin(String login);
}
