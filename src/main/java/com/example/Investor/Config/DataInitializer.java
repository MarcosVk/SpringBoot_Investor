package com.example.Investor.Config;

import com.example.Investor.Entity.Role;
import com.example.Investor.Repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner init(RoleRepository roleRepository){
        return args -> {
            if(roleRepository.findByName("ROLE_INVESTOR").isEmpty()){
                roleRepository.save(new Role(null,"ROLE_INVESTOR"));
            }
            if(roleRepository.findByName("ROLE_ADMIN").isEmpty()){
                roleRepository.save(new Role(null,"ROLE_ADMIN"));
            }
        };
    }
}
