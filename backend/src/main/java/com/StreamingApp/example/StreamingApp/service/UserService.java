package com.StreamingApp.example.StreamingApp.service;

import com.StreamingApp.example.StreamingApp.dto.UserInfoDto;
import com.StreamingApp.example.StreamingApp.dto.VideoDto;
import com.StreamingApp.example.StreamingApp.model.User;
import com.StreamingApp.example.StreamingApp.model.Video;
import com.StreamingApp.example.StreamingApp.repository.UserRepository;
import com.StreamingApp.example.StreamingApp.repository.VideoRepository;
import lombok.AllArgsConstructor;
import org.springframework.expression.ExpressionException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final VideoRepository videoRepository;

    public User getCurrentUser(){
       String sub= ((Jwt)(SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getClaim("sub");

        return userRepository.findBySub(sub)
              .orElseThrow(() -> new IllegalArgumentException("cannot find user with sub -" + sub));
    }

    public void addToLikedVideos(String videoId) {
        User currentUser=getCurrentUser();
        currentUser.addToLikedVideos(videoId);
        userRepository.save(currentUser);
    }
    public boolean ifLikedVideo(String videoId){
       return getCurrentUser().getLikedVideos().stream().anyMatch(id -> id.equals(videoId));
    }
    public boolean ifDisLikedVideo(String videoId){
        return getCurrentUser().getDisLikedVideos().stream().anyMatch(id -> id.equals(videoId));
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
    public List<String> getVideoHistory() {
        User currentUser = getCurrentUser();
        return currentUser.getVideoHistory().stream().collect(Collectors.toList());
    }
    public void addVideoToHistory(String videoId) {
        User currentUser=getCurrentUser();
        currentUser.addVideoToHistory(videoId);
        userRepository.save(currentUser);
    }
    public List<String> getLikedVideo() {
        User currentUser = getCurrentUser();
        return currentUser.getLikedVideos().stream().collect(Collectors.toList());
    }
    public void addToLikedVideo(String videoId) {
        User currentUser=getCurrentUser();
        currentUser.addVideoToLikedVideos(videoId);
        userRepository.save(currentUser);
    }

    public void subscribeUser(String userId) {
        var currentUser = getCurrentUser();
        currentUser.addToSubscribedUsers(userId);
        var subscribedToUser = userRepository.findById(userId).orElseThrow(() -> new ExpressionException("Invalid User - " + userId));
        subscribedToUser.addToSubscribers(subscribedToUser.getId());
        userRepository.save(currentUser);
        userRepository.save(subscribedToUser);
    }

    public void unSubscribeUser(String userId) {
        var currentUser = getCurrentUser();
        currentUser.removeFromSubscribedUsers(userId);

        var subscribedToUser = userRepository.findById(userId)
                .orElseThrow(() -> new ExpressionException("Invalid User - " + userId));
        subscribedToUser.removeFromSubscribers(subscribedToUser.getId());

        userRepository.save(currentUser);
        userRepository.save(subscribedToUser);
    }

    public Set<String> getLikedVideos(String userId) {
        var user=userRepository.findBySub(userId).orElseThrow(()->new ExpressionException("Invalid User - " + userId));
//                findById(userId).orElseThrow(()->new ExpressionException("Invalid User - " + userId));
        return user.getLikedVideos();
    }

    public UserInfoDto getUser(String userId) {
        User user = userRepository.findBySub(userId).orElseThrow(()->new ExpressionException("Invalid User - " + userId));
        UserInfoDto userInfoDto = new UserInfoDto();
        userInfoDto.setId(user.getId());
        userInfoDto.setSub(user.getSub());
        userInfoDto.setGivenName(user.getFirstName());
        userInfoDto.setFamilyName(user.getLastName());
        userInfoDto.setName(user.getFullName());
        userInfoDto.setPicture(user.getPicture());
        userInfoDto.setEmail(user.getEmail());

        return userInfoDto;
    }





    public List<VideoDto> getLikedVideos() {
        User currentUser = getCurrentUser();
        Set<String> likedVideoIds = currentUser.getLikedVideos();

        return likedVideoIds.stream()
                .map(videoId -> videoRepository.findById(videoId)
                        .map(VideoDto::fromVideo)
                        .orElseThrow(() -> new IllegalArgumentException("Video not found with id - " + videoId)))
                .collect(Collectors.toList());
    }


}
