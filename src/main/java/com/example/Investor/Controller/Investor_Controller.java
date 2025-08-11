package com.example.Investor.Controller;

import com.example.Investor.DTO.Investor_DTO;
import com.example.Investor.Entity.Investor_Entity;
import com.example.Investor.Service.Investor_Service;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@RestController
@RequestMapping(path = "api/v1/investor")
public class Investor_Controller {

    private final Investor_Service investorService;

    @PostMapping
    public void postInvestor(@RequestBody Investor_DTO investorDto){
         investorService.postInvestorService(investorDto);
    }

    @GetMapping
    public List<Investor_DTO> getInvestors(){
        return investorService.getInvestorsService();
    }

    @GetMapping("{id}")
    public Investor_DTO getInvestor(@PathVariable("id") Integer id){
        return investorService.getInvestorService(id);
    }

    @PutMapping("{id}")
    public void updateInvestor(@PathVariable("id") Integer id,
                               @RequestParam(required = false) String investor_name,
                               @RequestParam(required = false) String investor_email,
                               @RequestParam(required = false) String investor_phoneNumber,
                               @RequestParam(required = false) String investor_panNumber){
        investorService.updateInvestorService(id,investor_name,investor_email,investor_phoneNumber,investor_panNumber);

    }
    @DeleteMapping("{id}")
    public void deleteInvestor(@PathVariable("id") Integer id){
        investorService.deleteInvestorService(id);
    }
}
