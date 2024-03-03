package com.StreamingApp.example.StreamingApp.controller;

import com.StreamingApp.example.StreamingApp.service.UserRegistrationService;
import com.StreamingApp.example.StreamingApp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRegistrationService userRegistrationService;

    @GetMapping("/register")
    public String register(Authentication authentication){
        Jwt jwt= (Jwt)authentication.getPrincipal();
        return  userRegistrationService.registerUser(jwt.getTokenValue());

    }

    @PostMapping("subscribe/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public boolean subscribeToUser(@PathVariable String userId){
        userService.subscribeUser(userId);
        return true;
    }



    @PostMapping("unSubscribe/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public boolean unSubscribeToUser(@PathVariable String userId){
        userService.unSubscribeUser(userId);
        return true;
    }

}
