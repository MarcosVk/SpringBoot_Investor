package com.example.Investor.Service;

import com.example.Investor.DTO.AuthRequest;
import com.example.Investor.DTO.AuthResponse;
import com.example.Investor.DTO.RegisterRequest;
import com.example.Investor.Entity.Role;
import com.example.Investor.Entity.UserEntity;
import com.example.Investor.Exception.ResourceNotFoundException;
import com.example.Investor.Mapper.UserMapper;
import com.example.Investor.Repository.RoleRepository;
import com.example.Investor.Repository.UserRepository;
import com.example.Investor.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public AuthResponse PostUserService(AuthRequest authRequest){
        Authentication authentication=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(),authRequest.getPassword())
        );
        UserDetails userDetails=(UserDetails)authentication.getPrincipal();
        String generatedToken=jwtTokenProvider.generateToken(userDetails);
        return new AuthResponse(generatedToken);
    }
    public RegisterRequest PostRegisterService(RegisterRequest request,Boolean IsAdmin){
        UserEntity entity=userMapper.toUserEntity(request);

        Role investorRole;
        if(IsAdmin){
            investorRole=roleRepository.findByName("ROLE_ADMIN").orElseThrow(
                    ()->new ResourceNotFoundException("Admin not found"));
            entity.setRoles(Set.of(investorRole));
        }else{
            investorRole=roleRepository.findByName("ROLE_INVESTOR").orElseThrow(
                    ()->new ResourceNotFoundException("Investor not found"));
        }
        entity.setRoles(Set.of(investorRole));
        entity.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(entity);
        return userMapper.toRegisterRequest(entity);
    }
}
