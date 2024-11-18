package com.rajaayush.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.UUID;

@Data
public class CreateTransactionRequest {
//    @NotBlank(message = "Transaction amount is required")
    private double amount;

    private UUID sender;

    private UUID receiver;
}
