package com.StreamingApp.example.StreamingApp.dto;

import com.StreamingApp.example.StreamingApp.model.VideoStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
