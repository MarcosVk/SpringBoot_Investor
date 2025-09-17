package com.example.Investor.Mapper;

import com.example.Investor.DTO.PortfolioDTO;
import com.example.Investor.DTO.PortfolioRequest;
import com.example.Investor.Entity.Investor;
import com.example.Investor.Entity.Portfolio;
import com.example.Investor.Exception.ResourceNotFoundException;
import com.example.Investor.Repository.InvestorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PortfolioMapper {
    private final InvestorRepository repository;

    public PortfolioDTO EntityToPortfolioDTO(Portfolio portfolio){
        PortfolioDTO portfolioDTO=new PortfolioDTO();
        portfolioDTO.setId(portfolio.getId());
        portfolioDTO.setType(portfolio.getType());
        portfolioDTO.setValue(portfolio.getValue());
        portfolioDTO.setName(portfolio.getName());
        if(portfolio.getInvestor()!=null){
            portfolioDTO.setInvestor_id(portfolio.getInvestor().getInvestor_id());
            portfolioDTO.setInvestorName(portfolio.getInvestor().getName());
        }
        return portfolioDTO;
    }
    public Portfolio PortfolioDtoToEntity(PortfolioDTO portfolioDTO,Investor investor){
        Portfolio portfolio=new Portfolio();
        portfolio.setId(portfolioDTO.getId());
        portfolio.setType(portfolioDTO.getType());
        portfolio.setValue(portfolioDTO.getValue());
        portfolio.setName(portfolioDTO.getName());
        portfolio.setInvestor(investor);
        return portfolio;
    }
    public Portfolio PortfolioReqToEntity(PortfolioRequest request){
        Portfolio portfolio=new Portfolio();
        portfolio.setType(request.getType());
        portfolio.setValue(request.getValue());
        portfolio.setName(request.getName());
        Investor investor=repository.findById(request.getInvestor_id())
                .orElseThrow(()-> new ResourceNotFoundException("Investor not found with id: "+request.getInvestor_id()));
        portfolio.setInvestor(investor);
        return portfolio;
    }
    public PortfolioRequest EntityToPortfolioReq(Portfolio portfolio){
        PortfolioRequest request=new PortfolioRequest();
        request.setType(portfolio.getType());
        request.setValue(portfolio.getValue());
        request.setName(portfolio.getName());
        request.setInvestor_id(portfolio.getId());
        return request;
    }
}
