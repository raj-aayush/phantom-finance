package com.rajaayush.api.dto;

import lombok.Data;

@Data
public class CreateTransactionRequest {
//    @NotBlank(message = "Transaction amount is required")
    private double amount;

    private String sender;

    private String receiver;
}
