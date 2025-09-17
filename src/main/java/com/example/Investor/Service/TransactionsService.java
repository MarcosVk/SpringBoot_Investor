package com.example.Investor.Service;

import com.example.Investor.DTO.TransactionsDTO;
import com.example.Investor.DTO.TransactionsRequest;
import com.example.Investor.Entity.Portfolio;
import com.example.Investor.Entity.Transactions;
import com.example.Investor.Exception.ResourceNotFoundException;
import com.example.Investor.Mapper.TransactionsMapper;
import com.example.Investor.Repository.PortfolioRepository;
import com.example.Investor.Repository.TransactionsRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


@Service
@RequiredArgsConstructor
public class TransactionsService {
    private final TransactionsRepository transactionsrepository;
    private final PortfolioRepository portfolioRepository;
    private final TransactionsMapper transactionsMapper;

    private static final Logger log= LoggerFactory.getLogger(TransactionsService.class);

    public TransactionsRequest PostTransactionService(Integer id,TransactionsRequest request){
        log.info("Making a transaction....!");
        Portfolio portfolio=portfolioRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Portfolio not found: "+id));
        Transactions transactions=transactionsMapper.RequestToEntity(request);
        transactions.setPortfolio(portfolio);
        LocalDate currentDate=LocalDate.now();
        transactions.setDate(currentDate);
        Transactions savedTransactions=transactionsrepository.save(transactions);
        log.info("Created transaction sucessfully for portfolio {}",portfolio.getName());
        return transactionsMapper.EntityToRequest(savedTransactions);
    }
    public Page<TransactionsDTO> GetTransactionsService(Pageable pageable){
        return  transactionsrepository.findAll(pageable).map(transactionsMapper::EntityToDto);
    }
    public Page<TransactionsDTO> GetTransactionsByPortfolioService(Integer id,Pageable pageable){
        Portfolio portfolio=portfolioRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Portfolio not found: "+id));
        return  transactionsrepository.findByPortfolio(portfolio,pageable).map(transactionsMapper::EntityToDto);
    }
}
