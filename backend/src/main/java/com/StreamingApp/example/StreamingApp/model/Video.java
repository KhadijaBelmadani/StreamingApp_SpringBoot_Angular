package com.StreamingApp.example.StreamingApp.model;



import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "Videos")
@Builder
@Getter
@Setter
public class Video {

    private String id;
    private String title;
    private String description;
    //foreign keys
    private String userId;
    private String categoryId;
    //
    private AtomicInteger likes = new AtomicInteger(0);
    private AtomicInteger disLikes = new AtomicInteger(0);
    private List<String> tags;
    private String url;
    private VideoStatus videoStatus;
    private AtomicInteger viewCount = new AtomicInteger(0);
    private String thumbnailUrl;
    private List<Comment> comments = new ArrayList<>();

    public void incrementLikes(){
        likes.incrementAndGet();
    }

    public void decrementLikes(){likes.decrementAndGet();}

    public void incrementDisLikes(){
        disLikes.incrementAndGet();
    }

    public void decrementDisLikes(){
        disLikes.decrementAndGet();
    }


    public void incrementViewCount() {
        viewCount.incrementAndGet();
    }
}

