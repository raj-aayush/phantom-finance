package com.rajaayush.api.dto;

import lombok.Data;

@Data
public class CreateCustomerRequest {

    //    @NotBlank(message = "Customer ID is required")
    private String firstName;

    private String email;

    private double initialAmount = 0.0;
}
