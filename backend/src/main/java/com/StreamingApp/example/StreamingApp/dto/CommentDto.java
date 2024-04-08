package com.StreamingApp.example.StreamingApp.dto;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CommentDto {
    @NotBlank
    private String id;
    @NotBlank
    private String text;
    @NotBlank
    private  String author;
    @NotBlank
    private Date creationDate;
    @Min(value = 0)
    private int likeCount;
    @Min(value = 0)
    private int disLikeCount;

}

