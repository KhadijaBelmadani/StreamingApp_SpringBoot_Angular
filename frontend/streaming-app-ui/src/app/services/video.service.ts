import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {UploadVideoResponse} from "../upload-video/UploadVideoResponse";
import {VideoDto} from "../VideoDto";

@Injectable({
  providedIn: 'root'
})
export class VideoService {

  constructor(private httpClient:HttpClient) { }

  uploadVideo(fileEntry: File):Observable<UploadVideoResponse> {
    const formData = new FormData();
    formData.append("file", fileEntry, fileEntry.name);
    return this.httpClient.post<UploadVideoResponse>("http://localhost:8080/api/videos",formData) ;

  }
  uploadThumbnail(fileEntry: File,videoId:string):Observable<string> {
    const formData = new FormData();
    formData.append("file", fileEntry, fileEntry.name);
    formData.append('videoId',videoId);
    return this.httpClient.post("http://localhost:8080/api/videos/thumbnail",formData,{
    responseType:'text'

  });

}
  getVideo(videoId:string):Observable<VideoDto>{
    return   this.httpClient.get<VideoDto>("http://localhost:8080/api/videos/"+ videoId);
  }

  saveVideo(videoMetaData: VideoDto):Observable<VideoDto> {
     return this.httpClient.put<VideoDto>("http://localhost:8080/api/videos",videoMetaData);

  }
}
