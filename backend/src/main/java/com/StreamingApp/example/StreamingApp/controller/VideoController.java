package com.StreamingApp.example.StreamingApp.controller;

import com.StreamingApp.example.StreamingApp.dto.CommentDto;
import com.StreamingApp.example.StreamingApp.dto.UploadVideoResponse;
import com.StreamingApp.example.StreamingApp.dto.VideoDto;
import com.StreamingApp.example.StreamingApp.model.User;
import com.StreamingApp.example.StreamingApp.model.Video;
import com.StreamingApp.example.StreamingApp.service.UserService;
import com.StreamingApp.example.StreamingApp.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/videos")
@RequiredArgsConstructor
public class VideoController {
    private final VideoService videoService;
    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UploadVideoResponse uploadVideo(@RequestParam("file")MultipartFile file){
        return videoService.uploadVideo(file);

    }


    @PostMapping("/thumbnail")
    @ResponseStatus(HttpStatus.CREATED)
    public String uploadThumbnail(@RequestParam("file")MultipartFile file,@RequestParam("videoId") String videoId){

       return videoService.uploadThumbnail(file,videoId);

    }


    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public VideoDto editVideoMetaData(@RequestBody VideoDto videoDto)
    {
        return  videoService.editVideo(videoDto);
    }

    @GetMapping("/{videoId}")
    @ResponseStatus(HttpStatus.OK)
    public VideoDto getVideoDetails(@PathVariable String videoId){
      return videoService.getVideoDetails(videoId);
    }

    @PostMapping("/{videoId}/like")
    @ResponseStatus(HttpStatus.OK)
    public VideoDto likeVideo(@PathVariable String videoId){
        return videoService.likeVideo(videoId);
    }

    @PostMapping("/{videoId}/dislike")
    @ResponseStatus(HttpStatus.OK)
    public VideoDto dislikeVideo(@PathVariable String videoId){
        return videoService.dislikeVideo(videoId);
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<VideoDto> getVideoMetaData() {
        return videoService.getAllVideos();
    }

    @PostMapping("/{videoId}/comment")
    @ResponseStatus(HttpStatus.CREATED)
    public void addComments(@PathVariable String videoId, @RequestBody CommentDto commentDto) {
        videoService.addComment(commentDto, videoId);
    }
    @DeleteMapping("/{videoId}/comments/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable String videoId, @PathVariable String commentId) {
        videoService.deleteComment(videoId, commentId);
    }

    @GetMapping("/{videoId}/comment")
    @ResponseStatus(HttpStatus.OK)
    public List<CommentDto> getAllComments(@PathVariable String videoId) {
        return videoService.getAllComments(videoId);
    }

    @GetMapping("suggested/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<VideoDto> getSuggestedVideos(@PathVariable String userId) {
        return videoService.getSuggestedVideos(userId);
    }

    @DeleteMapping("{videoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteVideo(@PathVariable String videoId) {
        videoService.deleteVideo(videoId);
    }

    @GetMapping("channel/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<VideoDto> allChannelVideos(@PathVariable String userId) {
        return videoService.getAllVideosByChannel(userId);
    }



    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Video>> searchVideos(@RequestParam("title") String title) {
        List<Video> videos = videoService.searchVideosByTitle(title);
        return new ResponseEntity<>(videos, HttpStatus.OK);
    }
}
