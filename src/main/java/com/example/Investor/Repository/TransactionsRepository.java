package com.example.Investor.Repository;

import com.example.Investor.Entity.Portfolio;
import com.example.Investor.Entity.Transactions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionsRepository extends JpaRepository<Transactions,Integer> {
    Page<Transactions> findByPortfolio(Portfolio portfolio, Pageable pageable);
}
