package com.example.Investor.Service;

import com.example.Investor.DTO.InvestorDTO;
import com.example.Investor.DTO.InvestorRequest;
import com.example.Investor.Entity.Investor;
import com.example.Investor.Exception.ResourceNotFoundException;
import com.example.Investor.Mapper.InvestorMapper;
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

@RequiredArgsConstructor
@Service
public class InvestorService {
    private final InvestorRepository investorRepository;
    private final InvestorMapper mapper;
    private static final Logger log= LoggerFactory.getLogger(InvestorService.class);

    public InvestorRequest postInvestorService(InvestorRequest investorRequest){
        log.info("Creating investor: {}", investorRequest.getName());
        Investor investorEntity = mapper.ConvertRequestToEntity(investorRequest);
        LocalDate createdDate=LocalDate.now();
        investorEntity.setCreatedDate(createdDate);
        Investor savedInvestor= investorRepository.save(investorEntity);
        log.info("Created Investor: {} sucessfully",investorRequest.getName());
        return mapper.ConvertEntityToRequest(savedInvestor);
    }
    public Page<InvestorDTO> getInvestorsService(Pageable pageable){
        return investorRepository.findAll(pageable)
                .map(mapper::ConvertEntityToDTO);
    }
    public InvestorDTO getInvestorService(Integer id) {
        Investor entity= investorRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Investor not found: "+id));
        return mapper.ConvertEntityToDTO(entity);
    }
    @Transactional
    public InvestorRequest updateInvestorService(Integer id,InvestorRequest investorRequest){
        log.info("Updating investor: {}", investorRequest.getName());
        Investor investorEntity=investorRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Investor not found: "+id));
        boolean isUpdated=false;
        if(StringUtils.isNotBlank(investorRequest.getName())) {
            if (!(investorEntity.getName().equals(investorRequest.getName()))) {
                investorEntity.setName(investorRequest.getName());
                isUpdated=true;
            }
        }
        if(StringUtils.isNotBlank(investorRequest.getEmail())) {
            if (!(investorEntity.getEmail().equals(investorRequest.getEmail()))) {
                investorEntity.setEmail(investorRequest.getEmail());
                isUpdated=true;
            }
        }
        if(StringUtils.isNotBlank(investorRequest.getPhoneNumber())) {
            if (!(investorEntity.getPhoneNumber().equals(investorRequest.getPhoneNumber()))) {
                investorEntity.setPhoneNumber(investorRequest.getPhoneNumber());
                isUpdated=true;
            }
        }
        if(StringUtils.isNotBlank(investorRequest.getPanNumber())){
            if(!(investorEntity.getPanNumber().equals(investorRequest.getPanNumber()))){
                investorEntity.setPanNumber(investorRequest.getPanNumber());
                isUpdated=true;
            }
        }
        if(isUpdated){
            Investor updatedInvestor=investorRepository.save(investorEntity);
            log.info("Investor with ID: {} updated successfully", id);
            return mapper.ConvertEntityToRequest(updatedInvestor);
        }else{
            log.info("No changes detected for investor with ID: {}", id);
            return mapper.ConvertEntityToRequest(investorEntity);
        }
    }
    public void deleteInvestorService(Integer id){
        investorRepository.deleteById(id);
        log.info("Deleted Investor sucessfully...");
    }
}
