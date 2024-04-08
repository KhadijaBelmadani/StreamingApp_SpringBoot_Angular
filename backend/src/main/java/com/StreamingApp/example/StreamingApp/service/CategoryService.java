package com.StreamingApp.example.StreamingApp.service;

import com.StreamingApp.example.StreamingApp.dto.CategorySeedData;
import com.StreamingApp.example.StreamingApp.model.Category;
import com.StreamingApp.example.StreamingApp.model.Video;
import com.StreamingApp.example.StreamingApp.repository.CategoryRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> extractAndSaveCategories(List<Video> videos) {
        // Extract tags from videos
        List<String> allTags = videos.stream()
                .map(Video::getTags)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        // Count tag occurrences
        Map<String, Long> tagCounts = allTags.stream()
                .collect(Collectors.groupingBy(String::toLowerCase, Collectors.counting()));

        // Select most common tags (you can adjust the threshold as needed)
        List<String> commonTags = tagCounts.entrySet().stream()
                .filter(entry -> entry.getValue() > 1) // Adjust the threshold as needed
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        // Create categories from common tags
        List<Category> categories = commonTags.stream()
                .map(tag -> new Category(null, tag, new ArrayList<>()))
                .collect(Collectors.toList());

        // Save categories to database
        // Assuming you have a CategoryRepository to save categories
        categoryRepository.saveAll(categories);

        return categories;
    }

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
