package com.rajaayush.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.UUID;

@Data
public class CreateAccountRequest {

    @NotBlank(message = "Customer ID is required")
    private UUID customerId;

    private double balance = 0.0;
}
