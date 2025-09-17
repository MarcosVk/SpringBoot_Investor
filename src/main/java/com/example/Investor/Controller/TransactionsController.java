package com.example.Investor.Controller;

import com.example.Investor.DTO.TransactionsDTO;
import com.example.Investor.DTO.TransactionsRequest;
import com.example.Investor.Service.TransactionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("transactions")
@RequiredArgsConstructor
public class TransactionsController {
    private final TransactionsService transactionsService;

    @PostMapping("/portfolio/{portfolioId}")
    public ResponseEntity<TransactionsRequest> PostTransactions(
            @PathVariable("portfolioId") Integer id, @RequestBody TransactionsRequest transactionsRequest){
        TransactionsRequest request=transactionsService.PostTransactionService(id,transactionsRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(request);
    }
    @GetMapping
    public Page<TransactionsDTO> GetTransactions(
            @PageableDefault(size = 10,sort = "date",direction = Sort.Direction.ASC) Pageable pageable){
        return transactionsService.GetTransactionsService(pageable);
    }
    @GetMapping("/portfolio/{portfolioId}")
    public Page<TransactionsDTO> GetTransactionsByPortfolio(
            @PathVariable("portfolioId") Integer id,@PageableDefault(size = 10,sort = "date",direction = Sort.Direction.ASC) Pageable pageable){
        return transactionsService.GetTransactionsByPortfolioService(id,pageable);
    }

}
