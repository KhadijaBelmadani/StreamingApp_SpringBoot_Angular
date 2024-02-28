package com.StreamingApp.example.StreamingApp.service;

import com.StreamingApp.example.StreamingApp.model.User;
import com.StreamingApp.example.StreamingApp.model.Video;
import com.StreamingApp.example.StreamingApp.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User getCurrentUser(){
       String sub= ((Jwt)(SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getClaim("sub");

        return   userRepository.findBySub(sub)
              .orElseThrow(() -> new IllegalArgumentException("cannot find user with sub -" + sub));
    }

    public void addToLikedVideos(String videoId) {
        User currentUser=getCurrentUser();
        currentUser.addToLikedVideos(videoId);
        userRepository.save(currentUser);
    }
    public boolean ifLikedVideo(String videoId){
       return getCurrentUser().getLikedVideos().stream().anyMatch(likedVideo -> likedVideo.equals(videoId));
    }
    public boolean ifDisLikedVideo(String videoId){
        return getCurrentUser().getDisLikedVideos().stream().anyMatch(likedVideo -> likedVideo.equals(videoId));
    }

    public void removeFromLikedVideos(String videoId) {
        User currentUser=getCurrentUser();
        getCurrentUser().removeFromLikedVideos(videoId);
        userRepository.save(currentUser);
    }



    public void removeFromDisLikedVideos(String videoId) {
        User currentUser=getCurrentUser();
        currentUser.removeFromDisLikedVideos(videoId);
        userRepository.save(currentUser);
    }

    public void addToDislikedVideos(String videoId) {
        User currentUser=getCurrentUser();
        currentUser.addToDisLikedVideos(videoId);
        userRepository.save(currentUser);
    }

    public void addVideoToHistory(String videoId) {
        User currentUser=getCurrentUser();
        currentUser.addVideoToHistory(videoId);
        userRepository.save(currentUser);
    }
}
