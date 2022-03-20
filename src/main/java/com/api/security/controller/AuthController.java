package com.api.security.controller;

import com.api.security.dto.JwtResponse;
import com.api.security.dto.LoginRequest;
import com.api.security.jwt.JwtUtils;
import com.api.security.service.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*",maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/token")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest){
        Authentication authentication =authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(),
                loginRequest.getPassword()
        ));
        String generatetoken = jwtUtils.generateJwtToken(authentication);
        CustomUserDetails customUserDetails= (CustomUserDetails) authentication.getPrincipal();
        List<String> roles = customUserDetails.getAuthorities().stream().map(item->item.getAuthority()).collect(Collectors.toList());

        return ResponseEntity.ok().body(new JwtResponse(generatetoken,customUserDetails.getUsername(),roles));
    }
}
