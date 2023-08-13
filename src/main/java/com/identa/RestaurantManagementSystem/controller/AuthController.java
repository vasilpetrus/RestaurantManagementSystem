package com.identa.RestaurantManagementSystem.controller;

import com.identa.RestaurantManagementSystem.entity.User;
import com.identa.RestaurantManagementSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * The Auth controller responsible for handling requests for authentication.
 */
@Controller
public class AuthController {

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }
}
