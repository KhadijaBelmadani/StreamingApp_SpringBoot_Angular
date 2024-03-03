package com.StreamingApp.example.StreamingApp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "Users")
public class User {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String fullName;
    private String picture;
    private String email;
    private String sub;
    private String nickName;
    private Set<String> subscribedToUsers = new HashSet<>();
    private Set<String> subscribers = new HashSet<>();
    private Set<String> videoHistory = new LinkedHashSet<>();
    private Set<String> likedVideos = ConcurrentHashMap.newKeySet();
    private Set<String> disLikedVideos = ConcurrentHashMap.newKeySet();

    public void addToLikedVideos(String videoId) {

        likedVideos.add(videoId);
    }

    public void removeFromLikedVideos(String videoId) {likedVideos.remove(videoId);
    }

    public void addToDisLikedVideos(String videoId) {
        disLikedVideos.add(videoId);
    }

    public void removeFromDisLikedVideos(String videoId) {
        disLikedVideos.remove(videoId);
    }

    public void addToVideoHistory(String videoId) {
        videoHistory.add(videoId);
    }

    public void addToSubscribedUsers(String userId) {
        subscribedToUsers.add(userId);
    }

    public void addToSubscribers(String userId) {
        subscribers.add(userId);
    }


    public void addVideoToHistory(String videoId) {
        videoHistory.add(videoId);
    }
}

