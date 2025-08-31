package com.example.Investor.Service;

import com.example.Investor.DTO.InvestorDTO;
import com.example.Investor.DTO.PortfolioDTO;
import com.example.Investor.DTO.PortfolioRequest;
import com.example.Investor.Entity.Investor;
import com.example.Investor.Entity.Portfolio;
import com.example.Investor.Exception.ResourceNotFoundException;
import com.example.Investor.Mapper.PortfolioMapper;
import com.example.Investor.Repository.InvestorRepository;
import com.example.Investor.Repository.PortfolioRepository;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PortfolioService {
    private final PortfolioRepository portfolioRepository;
    private final PortfolioMapper portfolioMapper;
    private static final Logger log= LoggerFactory.getLogger(PortfolioService.class);

    public PortfolioRequest PostPortfolioService(PortfolioRequest request){
        log.info("Creating portfolio {}",request.getName());
        Portfolio portfolio=portfolioMapper.PortfolioReqToEntity(request);
        Portfolio savedPortfolio=portfolioRepository.save(portfolio);
        return portfolioMapper.EntityToPortfolioReq(savedPortfolio);
    }
    @Transactional
    public void UpdatePortfolioService(Integer id,PortfolioRequest request){
        log.info("Updating portfolio {}", request.getName());
        Portfolio portfolio=portfolioRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Portfolio not found: "+id));
        if(StringUtils.isNotBlank(request.getName())){
            if(!request.getName().equals(portfolio.getName())){
                portfolio.setName(request.getName());
            }
        }
        if(StringUtils.isNotBlank(request.getType())){
            if(!request.getType().equals(portfolio.getType())){
                portfolio.setType(request.getType());
            }
        }
        if(request.getValue()!=null){
            if(!request.getValue().equals(portfolio.getValue())){
                portfolio.setValue(request.getValue());
            }
        }
    }
    public Page<PortfolioDTO> GetPortfoliosService(Pageable pageable){
        return portfolioRepository.findAll(pageable).map(portfolioMapper::EntityToPortfolioDTO);
    }
    public PortfolioDTO GetPortfolioService(Integer id){
        Portfolio portfolio=portfolioRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Portfolio not found: "+id));
        return portfolioMapper.EntityToPortfolioDTO(portfolio);
    }
    public void DeletePortfolioService(Integer id){
        portfolioRepository.deleteById(id);
    }
}
