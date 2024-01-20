package com.StreamingApp.example.StreamingApp.service;

import com.StreamingApp.example.StreamingApp.model.Video;
import com.StreamingApp.example.StreamingApp.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
@Service
@Setter
@RequiredArgsConstructor
public class VideoService {
    private final S3Service s3service;
    private final VideoRepository videoRepository;
    public void uploadVideo(MultipartFile multipartFile){
        String videoUrl=s3service.uploadFile(multipartFile);
        var video =new Video();
        video.setUrl(videoUrl);
        videoRepository.save(video);
    }
}
