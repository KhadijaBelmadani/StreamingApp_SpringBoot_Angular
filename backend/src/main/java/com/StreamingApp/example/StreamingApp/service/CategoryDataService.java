package com.StreamingApp.example.StreamingApp.service;

import com.StreamingApp.example.StreamingApp.dto.CategorySeedData;
import com.StreamingApp.example.StreamingApp.model.Category;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class CategoryDataService {
    @Autowired
    private MongoTemplate mongoTemplate;

    @PostConstruct
    public void seedCategories() {
        if (mongoTemplate.collectionExists(Category.class)) {
            return; // Skip seeding if collection already exists
        }
        mongoTemplate.insertAll(CategorySeedData.categories);
    }
}
