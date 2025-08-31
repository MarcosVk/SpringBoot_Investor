package com.example.Investor.DTO;

import com.example.Investor.Entity.Investor;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PortfolioDTO {
    private Integer id;
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Type is required")
    private String type;
    @NotNull(message = "Value is required")
    @Positive(message = "Value must be positive")
    private Long value;

    private Integer investor_id;
    private String investorName;
}
