package com.example.Investor.DTO;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Investor_DTO {

    private String investor_name;
    private String investor_email;
    private String investor_phoneNumber;
    private String investor_panNumber;
    private LocalDate investor_createdDate;

}
