package com.StreamingApp.example.StreamingApp.dto;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDto {
    @NotBlank
    private String commentText;
    @NotBlank
    private  String authorId;
    @Min(value = 0)
    private int likeCount;
    @Min(value = 0)
    private int disLikeCount;




}

