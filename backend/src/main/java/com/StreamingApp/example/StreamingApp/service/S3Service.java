package com.StreamingApp.example.StreamingApp.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.UUID;
@Service
@RequiredArgsConstructor

public class S3Service implements FileService {
    public static final String BUCKET_NAME = "app-log-storage";
    private final AmazonS3Client aws3Client;
    @Override
    public String uploadFile(MultipartFile file){

       var filenameExtension= StringUtils.getFilenameExtension(file.getOriginalFilename()) ;
        var key = UUID.randomUUID().toString() + filenameExtension;
        var metadata=new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        try{
            aws3Client.putObject(BUCKET_NAME,key,file.getInputStream(),metadata);

        }
        catch (IOException ioException){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"An Exception occurred while uploading the file");
        }
        aws3Client.setObjectAcl(BUCKET_NAME,key, CannedAccessControlList.PublicRead);
        return aws3Client.getResourceUrl(BUCKET_NAME,key);
    }
}
