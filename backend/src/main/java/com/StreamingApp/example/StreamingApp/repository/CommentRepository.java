package com.StreamingApp.example.StreamingApp.repository;

import com.StreamingApp.example.StreamingApp.model.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommentRepository extends MongoRepository<Comment,String> {
}
