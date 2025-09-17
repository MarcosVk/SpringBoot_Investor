package com.example.Investor.Mapper;

import com.example.Investor.DTO.TransactionsDTO;
import com.example.Investor.DTO.TransactionsRequest;
import com.example.Investor.Entity.Transactions;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransactionsMapper {

    public Transactions RequestToEntity(TransactionsRequest request){
        Transactions transactions=new Transactions();
        transactions.setType(request.getType());
        transactions.setAmount(request.getAmount());
        return transactions;
    }
    public TransactionsRequest EntityToRequest(Transactions transactions){
        TransactionsRequest request=new TransactionsRequest();
        request.setType(transactions.getType());
        request.setAmount(transactions.getAmount());
        return request;
    }
    public TransactionsDTO EntityToDto(Transactions transactions){
        TransactionsDTO dto=new TransactionsDTO();
        dto.setId(transactions.getId());
        dto.setDate(transactions.getDate());
        dto.setAmount(transactions.getAmount());
        dto.setType(transactions.getType());
        if(transactions.getPortfolio()!=null){
            dto.setPortfolio_id(transactions.getPortfolio().getId());
            dto.setPortfolio_name(transactions.getPortfolio().getName());
        }
        return dto;
    }

}
