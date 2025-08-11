package com.example.Investor.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="Investor")
public class Investor_Entity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer investor_id;

    private String investor_name;
    private String investor_email;
    private String investor_phoneNumber;
    private String investor_panNumber;
    private LocalDate investor_createdDate;
}
