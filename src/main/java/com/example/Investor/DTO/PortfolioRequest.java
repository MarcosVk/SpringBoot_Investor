package com.example.Investor.DTO;

import com.example.Investor.Entity.Investor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioRequest {
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Type is required")
    private String type;
    @NotNull(message = "Value is required")
    @Positive(message = "Value must be positive")
    private Long value;

    @NotNull(message="Investor ID is required")
    private Integer investor_id;
}
