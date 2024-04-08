package com.StreamingApp.example.StreamingApp.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.validation.constraints.Min;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(value = "Comments")
public class Comment {
    @Id
    private String id;
    private String text;
    private String author;
    private Date creationDate;

  @Min(value = 0)
    private AtomicInteger likeCount = new AtomicInteger(0);
  @Min(value = 0)
    private AtomicInteger disLikeCount = new AtomicInteger(0);

    public int likeCount() {
        return likeCount.get();
    }

    public int disLikeCount() {
        return disLikeCount.get();
    }
}
