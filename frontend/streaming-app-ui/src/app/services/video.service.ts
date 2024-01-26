import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class VideoService {

  constructor(private httpClient:HttpClient) { }

  uploadVideo(fileEntry: File):Observable<any> {
    const formData = new FormData();
    formData.append("file", fileEntry, fileEntry.name);
    return this.httpClient.post("http://localhost:8080/api/videos",formData) ;

  }
    // return fileEntry.file((file => {
    //   const userId = this.authService.getUserId();
    //
    //   fd.append("userId", userId !== null ? userId : '');
    //   return this.httpClient.post<UploadVideoResponse>('http://localhost:8080/api/video/upload', fd,
    //     {
    //       headers: new HttpHeaders().set('Authorization', 'Bearer ' + localStorage.getItem('access_token'))
    //     });
    // }))
}
