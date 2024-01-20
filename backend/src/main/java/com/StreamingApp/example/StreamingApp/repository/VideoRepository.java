package com.StreamingApp.example.StreamingApp.repository;

import com.StreamingApp.example.StreamingApp.model.Video;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VideoRepository extends MongoRepository<Video,String> {
}
