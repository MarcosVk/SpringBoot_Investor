package com.example.Investor.Service;

import com.example.Investor.DTO.PortfolioDTO;
import com.example.Investor.DTO.PortfolioRequest;
import com.example.Investor.Entity.Portfolio;
import com.example.Investor.Exception.ResourceNotFoundException;
import com.example.Investor.Mapper.PortfolioMapper;
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
        log.info("Created portfolio: {} sucessfully",request.getName());
        return portfolioMapper.EntityToPortfolioReq(savedPortfolio);
    }
    @Transactional
    public PortfolioRequest UpdatePortfolioService(Integer id,PortfolioRequest request){
        log.info("Updating portfolio {}", request.getName());
        Portfolio portfolio=portfolioRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Portfolio not found: "+id));
        boolean isUpdated=false;
        if(StringUtils.isNotBlank(request.getName())){
            if(!request.getName().equals(portfolio.getName())){
                portfolio.setName(request.getName());
                isUpdated=true;
            }
        }
        if(StringUtils.isNotBlank(request.getType())){
            if(!request.getType().equals(portfolio.getType())){
                portfolio.setType(request.getType());
                isUpdated=true;
            }
        }
        if(request.getValue()!=null){
            if(!request.getValue().equals(portfolio.getValue())){
                portfolio.setValue(request.getValue());
                isUpdated=true;
            }
        }
        if(isUpdated){
            Portfolio UpdatedPortfolio=portfolioRepository.save(portfolio);
            log.info("Portfolio with ID: {} updated successfully", id);
            return portfolioMapper.EntityToPortfolioReq(UpdatedPortfolio);
        }else{
            log.info("No changes detected for portfolio with ID: {}", id);
            return portfolioMapper.EntityToPortfolioReq(portfolio);
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
        log.info("Deleted Portfolio sucessfully...");
    }
}
