package com.example.Investor.Controller;

import com.example.Investor.DTO.AuthRequest;
import com.example.Investor.DTO.AuthResponse;
import com.example.Investor.DTO.RegisterRequest;
import com.example.Investor.Service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> PostUser(@Valid @RequestBody AuthRequest authRequest){
        AuthResponse authResponse=authService.PostUserService(authRequest);
        return ResponseEntity.ok(Map.of("token: ",authResponse.getJwtToken()));
    }
    @PostMapping("/register")
    public ResponseEntity<RegisterRequest> PostRegister(@Valid @RequestBody RegisterRequest request){
        RegisterRequest savedRegisterRequest=authService.PostRegisterService(request,false);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRegisterRequest);
    }
    @PostMapping("/admin/register")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RegisterRequest> PostRegisterAdmin(@Valid @RequestBody RegisterRequest request){
        RegisterRequest savedRegisterRequest=authService.PostRegisterService(request,true);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRegisterRequest);
    }

}
