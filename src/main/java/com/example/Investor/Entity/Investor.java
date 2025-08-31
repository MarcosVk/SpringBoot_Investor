package com.example.Investor.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="Investor")
public class Investor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer investor_id;

    private String name;
    private String email;
    private String phoneNumber;
    private String panNumber;
    private LocalDate createdDate;

    @OneToMany(mappedBy = "investor",cascade = CascadeType.ALL)
    List<Portfolio> portfolios;
}
