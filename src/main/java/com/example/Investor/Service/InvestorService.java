package com.example.Investor.Service;

import com.example.Investor.DTO.InvestorDTO;
import com.example.Investor.Entity.Investor;
import com.example.Investor.Exception.ResourceNotFoundException;
import com.example.Investor.Repository.InvestorRepository;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class InvestorService {
    private final InvestorRepository investorRepository;
    private static Logger log= LoggerFactory.getLogger(InvestorService.class);

    public InvestorDTO postInvestorService(InvestorDTO investorDto){
        log.info("Creating investor: {}", investorDto.getName());
        Investor investorEntity = ConvertDtoToEntity(investorDto);
        LocalDate createdDate=LocalDate.now();
        investorEntity.setCreatedDate(createdDate);
        Investor savedInvestor= investorRepository.save(investorEntity);
        return ConvertEntityToDto(savedInvestor);
    }

    public Page<InvestorDTO> getInvestorsService(Pageable pageable){
        return investorRepository.findAll(pageable)
                .map(this::ConvertEntityToDto);
    }

    public InvestorDTO getInvestorService(Integer id) {
        Investor entity= investorRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Investor not found: "+id));
        InvestorDTO dto=ConvertEntityToDto(entity);
        return dto;
    }
    @Transactional
    public void updateInvestorService(Integer id,InvestorDTO investorDTO){
        log.info("Updating investor: {}", investorDTO.getName());
        Investor investorEntity=investorRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Investor not found: "+id));
        if(StringUtils.isNotBlank(investorDTO.getName())) {
            if (!(investorEntity.getName().equals(investorDTO.getName()))) {
                investorEntity.setName(investorDTO.getName());
            }
        }
        if(StringUtils.isNotBlank(investorDTO.getEmail())) {
            if (!(investorEntity.getEmail().equals(investorDTO.getEmail()))) {
                investorEntity.setEmail(investorDTO.getEmail());
            }
        }
        if(StringUtils.isNotBlank(investorDTO.getPhoneNumber())) {
            if (!(investorEntity.getPhoneNumber().equals(investorDTO.getPhoneNumber()))) {
                investorEntity.setPhoneNumber(investorDTO.getPhoneNumber());
            }
        }
        if(StringUtils.isNotBlank(investorDTO.getPanNumber())){
            if(!(investorEntity.getPanNumber().equals(investorDTO.getPanNumber()))){
                investorEntity.setPanNumber(investorDTO.getPanNumber());
            }
        }
    }
    public void deleteInvestorService(Integer id){
        investorRepository.deleteById(id);
    }
    public Investor ConvertDtoToEntity(InvestorDTO investorDto){
        Investor entity=new Investor();
        entity.setName(investorDto.getName());
        entity.setEmail(investorDto.getEmail());
        entity.setPhoneNumber(investorDto.getPhoneNumber());
        entity.setPanNumber(investorDto.getPanNumber());
        entity.setCreatedDate(investorDto.getCreatedDate());
        return entity;
    }
    public InvestorDTO ConvertEntityToDto(Investor investorEntity){
        InvestorDTO investorDto=new InvestorDTO();
        investorDto.setName(investorEntity.getName());
        investorDto.setEmail(investorEntity.getEmail());
        investorDto.setPhoneNumber(investorEntity.getPhoneNumber());
        investorDto.setPanNumber(investorEntity.getPanNumber());
        investorDto.setCreatedDate(investorEntity.getCreatedDate());
        return investorDto;
    }
}
