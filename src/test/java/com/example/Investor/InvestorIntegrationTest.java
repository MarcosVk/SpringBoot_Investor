package com.example.Investor;

import com.example.Investor.DTO.InvestorDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class InvestorIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateInvestorAndGetAll() throws Exception{
        InvestorDTO investorDTO=new InvestorDTO();
        investorDTO.setName("Vignesh");
        investorDTO.setEmail("vignesh@example.com");
        investorDTO.setPhoneNumber("9876543210");
        investorDTO.setPanNumber("ABCDE1234F");

        mockMvc.perform(post("/api/v1/investor")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(investorDTO)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/v1/investor"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());
    }
}
