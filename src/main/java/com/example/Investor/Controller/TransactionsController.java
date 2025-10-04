package com.example.Investor.Controller;

import com.example.Investor.DTO.TransactionsDTO;
import com.example.Investor.DTO.TransactionsRequest;
import com.example.Investor.Service.TransactionsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionsController {
    private final TransactionsService transactionsService;
    @PreAuthorize("hasAnyRole('INVESTOR','ADMIN')")
    @PostMapping("/portfolio/{portfolioId}")
    public ResponseEntity<TransactionsRequest> PostTransactions(
            @PathVariable("portfolioId") Integer id,@Valid @RequestBody TransactionsRequest transactionsRequest) throws AccessDeniedException {
        TransactionsRequest request=transactionsService.PostTransactionService(id,transactionsRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(request);
    }
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<TransactionsDTO>> GetTransactions(
            @PageableDefault(size = 10,sort = "date",direction = Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.ok(transactionsService.GetTransactionsService(pageable));
    }
    @PreAuthorize("hasAnyRole('INVESTOR','ADMIN')")
    @GetMapping("/portfolio/{portfolioId}")
    public ResponseEntity<Page<TransactionsDTO>> GetTransactionsByPortfolio(
            @PathVariable("portfolioId") Integer id,@PageableDefault(size = 10,sort = "date",direction = Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.ok(transactionsService.GetTransactionsByPortfolioService(id,pageable));
    }

}
