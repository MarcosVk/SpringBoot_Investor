package com.example.Investor.Service;

import com.example.Investor.DTO.Investor_DTO;
import com.example.Investor.Entity.Investor_Entity;
import com.example.Investor.Repository.InvestorRepository;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class Investor_Service {
    private final InvestorRepository investorRepository;

    public Investor_DTO postInvestorService(Investor_DTO investorDto){
        Investor_Entity investorEntity = ConvertDtoToEntity(investorDto);
        LocalDate createdDate=LocalDate.now();
        investorEntity.setInvestor_createdDate(createdDate);
        Investor_Entity savedInvestor= investorRepository.save(investorEntity);
        return ConvertEntityToDto(savedInvestor);
    }

    public List<Investor_DTO> getInvestorsService(){
        List<Investor_Entity> allEntity= investorRepository.findAll();
        List<Investor_DTO> dto=new ArrayList<>();
        for(Investor_Entity entity:allEntity){
            dto.add(ConvertEntityToDto(entity));
        }
        return dto;
    }

    public Investor_DTO getInvestorService(Integer id) {
        Optional<Investor_Entity> entity= investorRepository.findById(id);
        Investor_Entity unwrap=entity.get();
        Investor_DTO dto=ConvertEntityToDto(unwrap);
        return dto;
    }
    @Transactional
    public void updateInvestorService(Integer id,String name,String email,
                               String phoneNumber, String panNumber){
        Investor_Entity investorEntity=investorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Investor not found with id: " + id));
        if(StringUtils.isNotBlank(name)) {
            if (!(investorEntity.getInvestor_name().equals(name))) {
                investorEntity.setInvestor_name(name);
            }
        }
        if(StringUtils.isNotBlank(email)) {
            if (!(investorEntity.getInvestor_email().equals(email))) {
                investorEntity.setInvestor_email(email);
            }
        }
        if(StringUtils.isNotBlank(phoneNumber)) {
            if (!(investorEntity.getInvestor_phoneNumber().equals(phoneNumber))) {
                investorEntity.setInvestor_phoneNumber(phoneNumber);
            }
        }
        if(StringUtils.isNotBlank(panNumber)){
            if(!(investorEntity.getInvestor_panNumber().equals(panNumber))){
                investorEntity.setInvestor_panNumber(panNumber);
            }
        }
    }
    public void deleteInvestorService(Integer id){
        investorRepository.deleteById(id);
    }
    public Investor_Entity ConvertDtoToEntity(Investor_DTO investorDto){
        Investor_Entity entity=new Investor_Entity();
        entity.setInvestor_name(investorDto.getInvestor_name());
        entity.setInvestor_email(investorDto.getInvestor_email());
        entity.setInvestor_phoneNumber(investorDto.getInvestor_phoneNumber());
        entity.setInvestor_panNumber(investorDto.getInvestor_panNumber());
        entity.setInvestor_createdDate(investorDto.getInvestor_createdDate());
        return entity;
    }
    public Investor_DTO ConvertEntityToDto(Investor_Entity investorEntity){
        Investor_DTO investorDto=new Investor_DTO();
        investorDto.setInvestor_name(investorEntity.getInvestor_name());
        investorDto.setInvestor_email(investorEntity.getInvestor_email());
        investorDto.setInvestor_phoneNumber(investorEntity.getInvestor_phoneNumber());
        investorDto.setInvestor_panNumber(investorEntity.getInvestor_panNumber());
        investorDto.setInvestor_createdDate(investorEntity.getInvestor_createdDate());
        return investorDto;
    }
}
