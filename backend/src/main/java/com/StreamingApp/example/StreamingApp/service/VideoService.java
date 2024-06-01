package com.StreamingApp.example.StreamingApp.service;

import com.StreamingApp.example.StreamingApp.dto.CommentDto;
import com.StreamingApp.example.StreamingApp.dto.UploadVideoResponse;
import com.StreamingApp.example.StreamingApp.dto.VideoDto;
import com.StreamingApp.example.StreamingApp.mapper.CommentMapper;
import com.StreamingApp.example.StreamingApp.mapper.VideoMapper;
import com.StreamingApp.example.StreamingApp.model.Comment;
import com.StreamingApp.example.StreamingApp.model.User;
import com.StreamingApp.example.StreamingApp.model.Video;
import com.StreamingApp.example.StreamingApp.repository.CommentRepository;
import com.StreamingApp.example.StreamingApp.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VideoService {
    private final S3Service s3service;
    @Autowired
    private final VideoRepository videoRepository;
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final CommentMapper commentMapper;
    private final VideoMapper videoMapper;

    public UploadVideoResponse uploadVideo(MultipartFile multipartFile) {
        String videoUrl = s3service.uploadFile(multipartFile);
        Video video = new Video();
        video.setUrl(videoUrl);
//        video.setUserId(userId);
        var savedVideo= videoRepository.save(video);
        return new UploadVideoResponse(savedVideo.getId(),savedVideo.getUrl());
    }

    public VideoDto editVideo(VideoDto videoDto) {
        // Find video by videoID
        var savedVideo = getVideoById(videoDto.getId());
        // Map the video fields to video
        savedVideo.setTitle(videoDto.getTitle());
        savedVideo.setDescription(videoDto.getDescription());
        savedVideo.setTags(videoDto.getTags());
//        savedVideo.setThumbnailUrl(videoDto.getThumbnailUrl()); // Set the thumbnailUrl
        savedVideo.setVideoStatus(videoDto.getVideoStatus());
        savedVideo.setUserId(videoDto.getUserId());
        // Save the video to DB
        videoRepository.save(savedVideo);
        return videoDto;
    }

    public String uploadThumbnail(MultipartFile file, String videoId)
    {
        var savedVideo= getVideoById(videoId);
        String thumbnailUrl= s3service.uploadFile(file);
        savedVideo.setThumbnailUrl(thumbnailUrl);
        videoRepository.save(savedVideo);
        return thumbnailUrl;
    }
    Video getVideoById(String videoId)
    {
        return videoRepository.findById(videoId)
                .orElseThrow(() -> new IllegalArgumentException("Cannot find video using id - " + videoId));

    }

    public VideoDto getVideoDetails(String videoId)
    {
        Video savedVideo=getVideoById(videoId);
        VideoDto videoDto = new VideoDto();
        increaseVideoCount(savedVideo);
        userService.addVideoToHistory(videoId);

        videoDto.setUrl(savedVideo.getUrl());
        videoDto.setThumbnailUrl(savedVideo.getThumbnailUrl());
        videoDto.setId(savedVideo.getId());
        videoDto.setTitle(savedVideo.getTitle());
        videoDto.setDescription(savedVideo.getDescription());
        videoDto.setTags(savedVideo.getTags());
        videoDto.setVideoStatus(savedVideo.getVideoStatus());
        videoDto.setLikeCount(savedVideo.getLikes().get());
        videoDto.setDislikeCount(savedVideo.getDisLikes().get());
        videoDto.setViewCount(savedVideo.getViewCount().get());
        videoDto.setUserId(savedVideo.getUserId());
//        videoDto.setFollowerCount(savedVideo.getFollowerCount().get());
        return videoDto;
    }

    private void increaseVideoCount(Video savedVideo) {
        savedVideo.incrementViewCount();
        videoRepository.save(savedVideo);
    }

    public VideoDto likeVideo(String videoId) {

        var videoById = getVideoById(videoId);

        if (userService.ifLikedVideo(videoId)) {
            videoById.decrementLikes();
            userService.removeFromLikedVideos(videoId);
        } else {
            if (userService.ifDisLikedVideo(videoId)) {
                videoById.decrementDisLikes();
                userService.removeFromDisLikedVideos(videoId);
            }
            videoById.incrementLikes();
            userService.addToLikedVideos(videoId);
        }

        videoRepository.save(videoById);
        return mapToVideoDto(videoById);
    }

    public VideoDto dislikeVideo(String videoId) {
        var videoById = getVideoById(videoId);

        if (userService.ifDisLikedVideo(videoId)) {
            videoById.decrementDisLikes();
            userService.removeFromDisLikedVideos(videoId);
        } else {
            if (userService.ifLikedVideo(videoId)) {
                videoById.decrementLikes();
                userService.removeFromLikedVideos(videoId);
            }
            videoById.incrementDisLikes();
            userService.addToDislikedVideos(videoId);
        }

        videoRepository.save(videoById);
        return mapToVideoDto(videoById);
    }
    private VideoDto mapToVideoDto(Video video) {
        User currentUser = userService.getCurrentUser();
//        boolean isSubscribed = false;
//        int followerCount = 0;
//        // Check if video and userId are not null
//        if (video != null && video.getUserId() != null) {
//            isSubscribed = currentUser.getSubscribedToUsers().contains(video.getUserId().getId());
//        }

        return VideoDto.builder()
                .id(video.getId())
                .userId(video.getUserId())
                .description(video.getDescription())
                .title(video.getTitle())
                .tags(video.getTags())
                .videoStatus(video.getVideoStatus())
                .url(video.getUrl())
                .thumbnailUrl(video.getThumbnailUrl())
                .likeCount(video.getLikes().get())
                .dislikeCount(video.getDisLikes().get())
                .viewCount(video.getViewCount().get())
                .comments(video.getComments())
//                .isSubscribed(isSubscribed)
//                .followerCount(followerCount) // Potential NPE here if getUserId() is null
                .build();
    }

    public List<VideoDto> getAllVideos() {
        List<Video> allVideos = videoRepository.findAll();
        System.out.println("All videos fetched: " + allVideos);
        return allVideos.stream()
                .map(this::mapToVideoDto)
                .collect(Collectors.toList());
    }

    public void addComment(CommentDto commentDto, String videoId) {
        var video = getVideoById(videoId);
        var comment = commentMapper.mapFromDto(commentDto);
        video.addComment(comment);
        videoRepository.save(video);

    }

    public List<CommentDto> getAllComments(String videoId) {
        return
                videoRepository.findById(videoId)
                .stream()
                .map(video -> commentMapper.mapToDtoList(video.getComments()))
                .findAny().orElse(Collections.emptyList());

    }

    public List<VideoDto> getSuggestedVideos(String userId) {
        Set<String> likedVideos=userService.getLikedVideos(userId);
        List<Video> likedVideoList=videoRepository.findByIdIn(likedVideos);
        List<String> tags=likedVideoList.stream()
                .map(Video::getTags)
                .flatMap(List::stream)
                .toList();
        return videoRepository.findByTagsIn(tags)
                .stream()
                .limit(5)
                .map(videoMapper::mapToDto)
                .toList();
    }

    public void deleteVideo(String videoId) {
        String videoUrl=getVideoById(videoId).getUrl();
        s3service.deleteFile(videoUrl);
    }

    public List<VideoDto> getAllVideosByChannel(String userId) {
        List<Video> videos=videoRepository.findByUserId(userId);
        return videos.stream()
                .map(videoMapper::mapToDto)
                .collect(Collectors.toList());
    }
    public void deleteComment(String videoId, String commentId) {
        // Retrieve the video by its ID
        Video video = getVideoById(videoId);

        // Find the comment to delete
        Optional<Comment> commentToDelete = video.getComments().stream()
                .filter(comment -> comment.getId().equals(commentId))
                .findFirst();

        // If comment is found, remove it from the list of comments
        commentToDelete.ifPresent(video.getComments()::remove);

        // Save the updated video to the database
        videoRepository.save(video);
    }



    public List<Video> searchVideosByTitle(String title) {

            return videoRepository.findByTitleContainingIgnoreCase(title);

    }



    public List<Video> getVideosByIds(List<String> videoIds) {
        return videoRepository.findAllById(videoIds);
    }

}
