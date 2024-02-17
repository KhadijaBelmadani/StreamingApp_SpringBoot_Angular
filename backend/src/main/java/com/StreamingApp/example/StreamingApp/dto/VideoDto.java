package com.StreamingApp.example.StreamingApp.dto;

import com.StreamingApp.example.StreamingApp.model.VideoStatus;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VideoDto {
    private String id;
//    private String userId;
//    private String categoryId;
    private String description;
    private String title;
    private List<String> tags;
    private VideoStatus videoStatus;
    private String url;
    private String thumbnailUrl;


}
