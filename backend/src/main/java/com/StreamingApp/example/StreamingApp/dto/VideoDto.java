package com.StreamingApp.example.StreamingApp.dto;

import com.StreamingApp.example.StreamingApp.model.Comment;
import com.StreamingApp.example.StreamingApp.model.User;
import com.StreamingApp.example.StreamingApp.model.Video;
import com.StreamingApp.example.StreamingApp.model.VideoStatus;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class VideoDto {
    private String id;
    private User userId;
    private String description;
    private String title;
    private List<String> tags;
    private VideoStatus videoStatus;
    private String url;
    private String thumbnailUrl;
    private Integer likeCount;
    private Integer dislikeCount;
    private Integer viewCount;
    private boolean isSubscribed;
    private Integer followerCount;
    private List<Comment> comments = new ArrayList<>();


    public static VideoDto fromVideo(Video video) {
        VideoDto dto = new VideoDto();
        dto.setId(video.getId());
        dto.setTitle(video.getTitle());
        dto.setDescription(video.getDescription());
        dto.setUrl(video.getUrl());
        return dto;
    }


}
