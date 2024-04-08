package com.StreamingApp.example.StreamingApp.dto;

import com.StreamingApp.example.StreamingApp.model.Category;

import java.util.Arrays;
import java.util.List;

public class CategorySeedData {
    public static final List<Category> categories = Arrays.asList(
            new Category("Comedy"),
            new Category("Action"),
            new Category("cinema")
    );
}
