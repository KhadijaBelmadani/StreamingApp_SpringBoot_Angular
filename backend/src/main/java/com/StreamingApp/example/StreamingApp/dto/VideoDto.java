package com.StreamingApp.example.StreamingApp.dto;

import com.StreamingApp.example.StreamingApp.model.VideoStatus;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class VideoDto {
    private String id;
    private String userId;
    private String description;
    private String title;
    private List<String> tags;
    private VideoStatus videoStatus;
    private String url;
    private String thumbnailUrl;
    private Integer likeCount;
    private Integer dislikeCount;
    private Integer viewCount;


}
