package com.StreamingApp.example.StreamingApp.repository;

import com.StreamingApp.example.StreamingApp.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User,String> {
    Optional<User> findBySub(String sub);

}
