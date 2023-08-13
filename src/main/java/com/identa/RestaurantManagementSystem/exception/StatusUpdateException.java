package com.identa.RestaurantManagementSystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class StatusUpdateException extends RuntimeException {
    public StatusUpdateException(String message) {
        super(message);
    }
}
