package com.StreamingApp.example.StreamingApp.repository;

import com.StreamingApp.example.StreamingApp.model.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoryRepository extends MongoRepository<Category,String> {
}
