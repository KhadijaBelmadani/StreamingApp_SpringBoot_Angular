package com.StreamingApp.example.StreamingApp.model;



import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.Date;
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
    @Id
    private String id;
    private String title;
    private String description;
    private Date createdAt = new Date();
    @Min(value = 0)
    private AtomicInteger likes = new AtomicInteger(0);
    @Min(value = 0)
    private AtomicInteger disLikes = new AtomicInteger(0);
    private List<String> tags;
    private String url;
    private VideoStatus videoStatus;
    private AtomicInteger viewCount = new AtomicInteger(0);
    private String thumbnailUrl;
    private List<Comment> comments = new ArrayList<>();
    private List<Category> categories;
    private User userId;

   private Category categoryId;



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

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public void deleteComment(Comment comment) {
        comments.remove(comment);
    }
}

