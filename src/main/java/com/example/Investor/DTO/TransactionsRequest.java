package com.example.Investor.DTO;

import com.example.Investor.Entity.Portfolio;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionsRequest {
    @NotBlank(message="Type cannot be null")
    private String type;
    @NotNull(message="Amount cannot be null")
    @Positive(message = "Amount must be positive")
    private double amount;

}
