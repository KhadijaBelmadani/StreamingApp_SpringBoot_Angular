package com.StreamingApp.example.StreamingApp.mapper;

import com.StreamingApp.example.StreamingApp.dto.VideoDto;
import com.StreamingApp.example.StreamingApp.model.Video;
import org.springframework.stereotype.Service;

@Service
public class VideoMapper {
    public VideoDto mapToDto(Video video){
        boolean isSubscriber = false;
        return VideoDto.builder()
                .id(video.getId())
                .userId(video.getUserId())
                .url(video.getUrl())
                .description(video.getDescription())
                .tags(video.getTags())
                .title(video.getTitle())
                .videoStatus(video.getVideoStatus())
                .userId(video.getUserId())
                .thumbnailUrl(video.getThumbnailUrl())
                .likeCount(video.getLikes().get())
                .dislikeCount(video.getDisLikes().get())
//                .followerCount(video.getFollowerCount().get())
//                .isSubscribed(isSubscriber)
                .build();


    }
}
