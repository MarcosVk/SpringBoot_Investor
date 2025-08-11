package com.example.Investor.Repository;

import com.example.Investor.Entity.Investor_Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvestorRepository extends JpaRepository<Investor_Entity,Integer> {
}
