package com.StreamingApp.example.StreamingApp.service;

import com.StreamingApp.example.StreamingApp.dto.UploadVideoResponse;
import com.StreamingApp.example.StreamingApp.dto.VideoDto;
import com.StreamingApp.example.StreamingApp.model.Video;
import com.StreamingApp.example.StreamingApp.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class VideoService {
    private final S3Service s3service;
    private final VideoRepository videoRepository;

    public UploadVideoResponse uploadVideo(MultipartFile multipartFile) {
        String videoUrl = s3service.uploadFile(multipartFile);
        Video video = new Video();
        video.setUrl(videoUrl);
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
        savedVideo.setThumbnailUrl(videoDto.getThumbnailUrl());
        savedVideo.setVideoStatus(videoDto.getVideoStatus());

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
        videoDto.setUrl(savedVideo.getUrl());
        videoDto.setThumbnailUrl(savedVideo.getThumbnailUrl());
        videoDto.setId(savedVideo.getId());
        videoDto.setTitle(savedVideo.getTitle());
        videoDto.setDescription(savedVideo.getDescription());
        videoDto.setTags(savedVideo.getTags());
        videoDto.setVideoStatus(savedVideo.getVideoStatus());
        return videoDto;
    }
}
