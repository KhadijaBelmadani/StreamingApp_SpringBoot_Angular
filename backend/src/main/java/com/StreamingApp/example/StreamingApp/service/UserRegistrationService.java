package com.StreamingApp.example.StreamingApp.service;

import com.StreamingApp.example.StreamingApp.dto.UserInfoDto;
import com.StreamingApp.example.StreamingApp.model.User;
import com.StreamingApp.example.StreamingApp.repository.UserRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserRegistrationService {

    @Value("${auth0.userinfoEndpoint}")
    private String userInfoEndPoint;

    private final UserRepository userRepository;

    public String registerUser(String tokenValue){
        //make a call to the userInfo Endpoint

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(userInfoEndPoint))
                .setHeader("Authorization", String.format("Bearer %s", tokenValue))
                .build();

        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();

        try{
           HttpResponse<String> responseString= httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            String body=responseString.body();

            ObjectMapper objectMapper=new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
            UserInfoDto userInfoDto= objectMapper.readValue(body, UserInfoDto.class);

            Optional <User> userBySubject= userRepository.findBySub(userInfoDto.getSub());
            if(userBySubject.isPresent()){
                return userBySubject.get().getId() ;
            }
            else{
                User user=new User();
                user.setSub(userInfoDto.getSub());
                user.setEmail(userInfoDto.getEmail());
                user.setFirstName(userInfoDto.getGivenName());
                user.setLastName(userInfoDto.getFamilyName());
                user.setFullName(userInfoDto.getName());
                user.setPicture(userInfoDto.getPicture());


                return userRepository.save(user).getId();

            }



        }catch(Exception exception){
            throw new RuntimeException("Exception occurred while registration user",exception);
        }
        //Fetch user details and save theme to the database


    }
}
