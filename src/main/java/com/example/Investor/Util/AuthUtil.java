package com.example.Investor.Util;

import com.example.Investor.Entity.UserEntity;
import com.example.Investor.Exception.ResourceNotFoundException;
import com.example.Investor.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.nio.file.AccessDeniedException;

@Component
@RequiredArgsConstructor
public class AuthUtil {

    private final UserRepository userRepository;

    public UserEntity getCurrentUser() throws AccessDeniedException {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();

        if(authentication==null || !authentication.isAuthenticated()){
            throw new AccessDeniedException("No authenticated user found");
        }
        String username=authentication.getName();

        return userRepository.findByUsername(username)
                .orElseThrow(()->new ResourceNotFoundException("User not found"));
    }
}
