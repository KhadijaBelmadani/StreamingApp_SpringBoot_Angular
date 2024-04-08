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
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VideoService {
    private final S3Service s3service;
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
        return videoDto;
    }

    private void increaseVideoCount(Video savedVideo) {
        savedVideo.incrementViewCount();
        videoRepository.save(savedVideo);
    }

    public VideoDto likeVideo(String videoId) {

        var videoById= getVideoById(videoId);

        if (userService.ifLikedVideo(videoId)) {
            videoById.decrementLikes();
            userService.removeFromLikedVideos(videoId);
        } else if (userService.ifDisLikedVideo(videoId)) {
            videoById.decrementDisLikes();
            userService.removeFromDisLikedVideos(videoId);
//            videoById.incrementLikes();
//            userService.addToLikedVideos(videoId);
        }else {
            videoById.incrementLikes();
            userService.addToLikedVideos(videoId);
        }
        videoRepository.save(videoById);
        return MapToVideoDto(videoById);
    }

    public VideoDto dislikeVideo(String videoId) {
        //Get video by id
        var videoById= getVideoById(videoId);
        if (userService.ifDisLikedVideo(videoId)) {
            videoById.decrementDisLikes();
            userService.removeFromDisLikedVideos(videoId);
        } else if (userService.ifLikedVideo(videoId)) {
            videoById.decrementLikes();
            userService.removeFromLikedVideos(videoId);
//            videoById.incrementDisLikes();
//            userService.addToDislikedVideos(videoId);
        }else {
            videoById.incrementDisLikes();
            userService.addToDislikedVideos(videoId);
        }
        videoRepository.save(videoById);
        return MapToVideoDto(videoById);
    }
    private VideoDto MapToVideoDto(Video videoById) {
        VideoDto videoDto = new VideoDto();
        videoDto.setUrl(videoById.getUrl());
        videoDto.setThumbnailUrl(videoById.getThumbnailUrl());
        videoDto.setId(videoById.getId());
        videoDto.setTitle(videoById.getTitle());
        videoDto.setDescription(videoById.getDescription());
        videoDto.setTags(videoById.getTags());
        videoDto.setVideoStatus(videoById.getVideoStatus());
        videoDto.setLikeCount(videoById.getLikes().get());
        videoDto.setDislikeCount(videoById.getDisLikes().get());
        videoDto.setViewCount(videoById.getViewCount().get());
        videoDto.setUserId(videoById.getUserId());
        return videoDto;
    }

    public List<VideoDto> getAllVideos() {
        List<Video> allVideos = videoRepository.findAll();
        return allVideos.stream()
                .map(this::MapToVideoDto)
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

    public List<VideoDto> findByTag(String query) {
        List<Video> videos=videoRepository.findByTagsContainingIgnoreCase(query);
        return videos.stream()
                .map(this::MapToVideoDto)
                .collect(Collectors.toList());
    }

    public List<VideoDto> searchVideos(String query) {

            List<Video> videos = videoRepository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(query, query);
            return videos.stream()
                    .map(this::MapToVideoDto)
                    .collect(Collectors.toList());
        }
    }
