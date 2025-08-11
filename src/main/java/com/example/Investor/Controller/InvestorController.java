package com.example.Investor.Controller;

import com.example.Investor.DTO.InvestorDTO;
import com.example.Investor.Service.InvestorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping(path = "api/v1/investor")
public class InvestorController {

    private final InvestorService investorService;

    @PostMapping
    public ResponseEntity<InvestorDTO> postInvestor(@Valid @RequestBody InvestorDTO investorDto){
         InvestorDTO savedDTO=investorService.postInvestorService(investorDto);
         return ResponseEntity.status(HttpStatus.CREATED).body(savedDTO);
    }

    @GetMapping
    public Page<InvestorDTO> getInvestors(@PageableDefault(size = 10,sort="name",direction = Sort.Direction.ASC)
                                          Pageable pageable){
        return investorService.getInvestorsService(pageable);
    }

    @GetMapping("{id}")
    public InvestorDTO getInvestor(@PathVariable("id") Integer id){
        return investorService.getInvestorService(id);
    }

    @PatchMapping("{id}")
    public void updateInvestor(@PathVariable("id") Integer id,
                                @RequestBody InvestorDTO investorDTO){
        investorService.updateInvestorService(id,investorDTO);

    }
    @DeleteMapping("{id}")
    public void deleteInvestor(@PathVariable("id") Integer id){
        investorService.deleteInvestorService(id);
    }
}
