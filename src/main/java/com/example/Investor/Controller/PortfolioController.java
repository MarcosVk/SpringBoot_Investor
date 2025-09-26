package com.example.Investor.Controller;

import com.example.Investor.DTO.PortfolioDTO;
import com.example.Investor.DTO.PortfolioRequest;
import com.example.Investor.Service.PortfolioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/portfolio")
@RequiredArgsConstructor
public class PortfolioController {
    private final PortfolioService service;
    @PostMapping
    public ResponseEntity<PortfolioRequest> PostPortfolio(@Valid @RequestBody PortfolioRequest request){
        PortfolioRequest portfolioRequest=service.PostPortfolioService(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(portfolioRequest);
    }
    @PatchMapping("{id}")
    public ResponseEntity<PortfolioRequest> UpdatePortfolio(@PathVariable("id") Integer id,
                                @RequestBody PortfolioRequest request){
        return ResponseEntity.ok(service.UpdatePortfolioService(id,request));
    }
    @GetMapping
    public ResponseEntity<Page<PortfolioDTO>> GetPortfolios(
            @PageableDefault(size = 10,sort = "name", direction = Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.ok(service.GetPortfoliosService(pageable));
    }
    @GetMapping("{id}")
    public ResponseEntity<PortfolioDTO> GetPortfolio(@PathVariable("id")Integer id){
        return ResponseEntity.ok(service.GetPortfolioService(id));
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Void> DeletePortfolio(@PathVariable("id")Integer id){
         service.DeletePortfolioService(id);
         return ResponseEntity.noContent().build();
    }
}
