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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

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
    @GetMapping("/debug-roles")
    public ResponseEntity<?> debugRoles(Authentication authentication) {
        System.out.println("=== DEBUG ROLES ===");
        System.out.println("Username: " + authentication.getName());
        System.out.println("Authorities: " + authentication.getAuthorities());

        boolean hasAdminRole = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
        boolean hasInvestorRole = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_INVESTOR"));

        System.out.println("Has ROLE_ADMIN: " + hasAdminRole);
        System.out.println("Has ROLE_INVESTOR: " + hasInvestorRole);
        System.out.println("=== END DEBUG ===");

        Map<String, Object> response = new HashMap<>();
        response.put("username", authentication.getName());
        response.put("authorities", authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));
        response.put("hasAdminRole", hasAdminRole);
        response.put("hasInvestorRole", hasInvestorRole);

        return ResponseEntity.ok(response);
    }
}
