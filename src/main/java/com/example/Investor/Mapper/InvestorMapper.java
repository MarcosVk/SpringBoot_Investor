package com.example.Investor.Mapper;

import com.example.Investor.DTO.InvestorDTO;
import com.example.Investor.DTO.InvestorRequest;
import com.example.Investor.DTO.PortfolioDTO;
import com.example.Investor.Entity.Investor;
import com.example.Investor.Entity.UserEntity;
import com.example.Investor.Util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InvestorMapper {
    private final PortfolioMapper portfolioMapper;
    private final AuthUtil authUtil;
    public Investor ConvertRequestToEntity(InvestorRequest investorRequest) throws AccessDeniedException {
        Investor entity=new Investor();
        entity.setName(investorRequest.getName());
        entity.setEmail(investorRequest.getEmail());
        entity.setPhoneNumber(investorRequest.getPhoneNumber());
        entity.setPanNumber(investorRequest.getPanNumber());
        UserEntity currentUser=authUtil.getCurrentUser();
        entity.setUserEntity(currentUser);
        return entity;
    }
    public InvestorRequest ConvertEntityToRequest(Investor investorEntity){
        InvestorRequest investorRequest=new InvestorRequest();
        investorRequest.setName(investorEntity.getName());
        investorRequest.setEmail(investorEntity.getEmail());
        investorRequest.setPhoneNumber(investorEntity.getPhoneNumber());
        investorRequest.setPanNumber(investorEntity.getPanNumber());
        return investorRequest;
    }
    public InvestorDTO ConvertEntityToDTO(Investor investor){
        InvestorDTO dto=new InvestorDTO();
        dto.setInvestor_id(investor.getInvestor_id());
        dto.setName(investor.getName());
        dto.setEmail(investor.getEmail());
        dto.setPhoneNumber(investor.getPhoneNumber());
        dto.setPanNumber(investor.getPanNumber());
        dto.setCreatedDate(investor.getCreatedDate());
        if(investor.getPortfolios()!=null){
            List<PortfolioDTO> portfolioDTOS=investor.getPortfolios().stream()
                    .map(portfolioMapper::EntityToPortfolioDTO).toList();
            dto.setPortfolios(portfolioDTOS);
        }
        return dto;
    }
}
