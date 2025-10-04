package com.example.Investor.Controller;

import com.example.Investor.DTO.AuthRequest;
import com.example.Investor.DTO.AuthResponse;
import com.example.Investor.DTO.RegisterRequest;
import com.example.Investor.DTO.UserDTO;
import com.example.Investor.Service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AuthService service;

    @PostMapping("/register")
    public ResponseEntity<RegisterRequest> registerAdmin(@RequestBody RegisterRequest request){
        RegisterRequest savedRegisterRequest=service.PostRegisterService(request,true);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRegisterRequest);
    }
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginAdmin(@RequestBody AuthRequest request){
        AuthResponse response=service.PostUserService(request);
        return ResponseEntity.ok(Map.of("token: ",response.getJwtToken()));
    }
    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<UserDTO>> getUsers(@PageableDefault(size = 10,sort = "username",direction = Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.ok(service.getAllUsers(pageable));
    }
}
