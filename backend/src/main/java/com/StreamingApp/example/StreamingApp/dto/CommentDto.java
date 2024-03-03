package com.StreamingApp.example.StreamingApp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private String commentText;
    private  String authorId;

//    public String getCommentAuthor() {
//    }
}

