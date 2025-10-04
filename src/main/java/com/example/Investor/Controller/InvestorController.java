package com.example.Investor.Controller;

import com.example.Investor.DTO.InvestorDTO;
import com.example.Investor.DTO.InvestorRequest;
import com.example.Investor.Service.InvestorService;
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


@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/investor")
public class InvestorController {

    private final InvestorService investorService;

    @PostMapping
    @PreAuthorize("hasAnyRole('INVESTOR','ADMIN')")
    public ResponseEntity<InvestorRequest> postInvestor(@Valid @RequestBody InvestorRequest investorRequest) throws AccessDeniedException {
         InvestorRequest savedRequest=investorService.postInvestorService(investorRequest);
         return ResponseEntity.status(HttpStatus.CREATED).body(savedRequest);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<InvestorDTO>> getInvestors(@PageableDefault(size = 10,sort="name",direction = Sort.Direction.ASC)
                                          Pageable pageable){
        return ResponseEntity.ok(investorService.getInvestorsService(pageable));
    }

    @GetMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<InvestorDTO> getInvestor(@PathVariable("id") Integer id){
        return ResponseEntity.ok(investorService.getInvestorService(id));
    }

    @PatchMapping("{id}")
    @PreAuthorize("hasAnyRole('INVESTOR','ADMIN')")
    public ResponseEntity<InvestorRequest> updateInvestor(@PathVariable("id") Integer id,
                                @RequestBody InvestorRequest investorRequest) throws AccessDeniedException {
        return ResponseEntity.ok(investorService.updateInvestorService(id,investorRequest));
    }
    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyRole('INVESTOR','ADMIN')")
    public ResponseEntity<Void> deleteInvestor(@PathVariable("id") Integer id) throws AccessDeniedException {
        investorService.deleteInvestorService(id);
        return ResponseEntity.noContent().build();
    }
}
