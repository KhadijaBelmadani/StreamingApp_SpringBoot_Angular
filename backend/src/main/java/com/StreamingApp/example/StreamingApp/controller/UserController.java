package com.StreamingApp.example.StreamingApp.controller;

import com.StreamingApp.example.StreamingApp.service.UserRegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserRegistrationService userRegistrationService;

    @GetMapping("/register")
    public String register(Authentication authentication){
        Jwt jwt= (Jwt)authentication.getPrincipal();
        userRegistrationService.registerUser(jwt.getTokenValue());
        return "User Registration successfully" ;
    }
}
