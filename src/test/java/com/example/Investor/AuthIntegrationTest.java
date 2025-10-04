package com.example.Investor;

import com.example.Investor.DTO.RegisterRequest;
import com.example.Investor.Repository.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void cleanUpBeforeTest() {
        try {
            userRepository.deleteByUsername("tony");
            userRepository.deleteByUsername("john");
            userRepository.deleteByUsername("admin2");
        } catch (Exception e) {
            System.out.println("User 'tony' not found for cleanup - this is expected");
        }
    }
    @AfterEach
    void cleanUpAfterTest() {
        userRepository.deleteByUsername("tony");
        userRepository.deleteByUsername("john");
        userRepository.deleteByUsername("admin2");
    }
    @Test
    void testRegisterLoginAndAccessProtectedEndpoint() throws Exception{
        RegisterRequest request=new RegisterRequest();
        request.setUsername("tony");
        request.setPassword("tony123");

        mockMvc.perform(post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        MvcResult result= mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn();

        String responseJson=result.getResponse().getContentAsString();
        JsonNode node=objectMapper.readTree(responseJson);
        String token=node.get("token: ").asText();

        mockMvc.perform(get("/api/v1/portfolio")
                .header("Authorization","Bearer "+token))
                .andExpect(status().isOk());
    }
    @Test
    void investorCannotAccessAdminEndpoints() throws Exception {
        RegisterRequest request=new RegisterRequest();
        request.setUsername("john");
        request.setPassword("john123");

        mockMvc.perform(post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        MvcResult result=mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                        .andExpect(status().isOk())
                        .andReturn();
        String responseJson=result.getResponse().getContentAsString();
        JsonNode node=objectMapper.readTree(responseJson);
        String token=node.get("token: ").asText();

        mockMvc.perform(get("/api/v1/auth/admin/users")
                .header("Authorization","Bearer "+token))
                .andExpect(status().isForbidden());
    }
    @Test
    void adminCanAccessInvestorAndAdminEndpoints() throws Exception {
        RegisterRequest request=new RegisterRequest();
        request.setUsername("admin2");
        request.setPassword("admin123");

        mockMvc.perform(post("/api/v1/auth/admin/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        MvcResult result=mockMvc.perform(post("/api/v1/auth/admin/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn();
        String responseJson=result.getResponse().getContentAsString();
        JsonNode node=objectMapper.readTree(responseJson);
        String token=node.get("token: ").asText();

        mockMvc.perform(get("/api/v1/auth/admin/users")
                        .header("Authorization","Bearer "+token))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/v1/investor")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }
}
