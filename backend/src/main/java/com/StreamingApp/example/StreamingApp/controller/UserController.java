package com.StreamingApp.example.StreamingApp.controller;

import com.StreamingApp.example.StreamingApp.dto.UserInfoDto;
import com.StreamingApp.example.StreamingApp.dto.VideoDto;
import com.StreamingApp.example.StreamingApp.model.User;
import com.StreamingApp.example.StreamingApp.repository.UserRepository;
import com.StreamingApp.example.StreamingApp.service.UserRegistrationService;
import com.StreamingApp.example.StreamingApp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private UserRepository userRepository;
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
//@PostMapping("/{userId}/subscribe")
//@ResponseStatus(HttpStatus.OK)
//public void subscribeToUser(@PathVariable String userId) {
//    String currentUserId = getCurrentUser().getId();
//    userService.subscribeUser(currentUserId, userId);
//}

//    @PostMapping("/{userId}/unsubscribe")
//    @ResponseStatus(HttpStatus.OK)
//    public void unsubscribeFromUser(@PathVariable String userId) {
//        String currentUserId = getCurrentUser().getId();
//        userService.unSubscribeUser(userId);
//    }

    private User getCurrentUser() {
        // Method to get the current authenticated user
        return userService.getCurrentUser();
    }

    @GetMapping("/{userId}")
    public UserInfoDto getUser(@PathVariable String userId) {
        return userService.getUser(userId);
    }

    @PostMapping("unSubscribe/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public boolean unSubscribeToUser(@PathVariable String userId){
        userService.unSubscribeUser(userId);
        return true;
    }

    @PostMapping("/history/{videoId}")
    public ResponseEntity<?> addVideoToHistory(@PathVariable String videoId) {
        userService.addVideoToHistory(videoId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/history")
    public ResponseEntity<List<String>> getVideoHistory() {
        List<String> videoHistory = userService.getVideoHistory();
        return ResponseEntity.ok(videoHistory);
    }

    @PostMapping("/favorite/{videoId}")
    public ResponseEntity<?> addVideoToLikedVideos(@PathVariable String videoId) {
        userService.addToLikedVideo(videoId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/favorite")
    public ResponseEntity<List<String>> getLikedVideosVideo() {
        List<String> likedVideo = userService.getLikedVideo();
        return ResponseEntity.ok(likedVideo);
    }


}
