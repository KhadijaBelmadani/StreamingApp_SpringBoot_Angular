package com.StreamingApp.example.StreamingApp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(value = "Categories")
public class Category {
    @Id
    private String id;
    private String name;
    private List<Video> videos = new ArrayList<>();

    public Category(String comedy) {
    }
}
