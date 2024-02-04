package com.StreamingApp.example.StreamingApp.controller;

import com.StreamingApp.example.StreamingApp.dto.VideoDto;
import com.StreamingApp.example.StreamingApp.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/videos")
@RequiredArgsConstructor
public class VideoController {
    private final VideoService videoService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void uploadVideo(@RequestParam("file")MultipartFile file){

        videoService.uploadVideo(file);

    }
    @PostMapping("/thumbnail")
    @ResponseStatus(HttpStatus.CREATED)
    public void uploadThumbnail(@RequestParam("file")MultipartFile file,@RequestParam("videoId") String videoId){

        videoService.uploadThumbnail(file,videoId);

    }
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public VideoDto editVideoMetaData(@RequestBody VideoDto videoDto)
    {

        return  videoService.editVideo(videoDto);
    }

}
