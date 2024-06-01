import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {VideoDto} from "./video-dto";
import {UserDto} from "./user-dto";

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private userId:string=''
  private baseUrl = 'http://localhost:8080/api/user';

  constructor(private httpClient:HttpClient) { }

  subscribeToUser(userId:String):Observable<boolean>{
  return this.httpClient.post<boolean>("http://localhost:8080/api/user/subscribe/"+userId,null);
  }

  registerUser() {
     this.httpClient.get("http://localhost:8080/api/user/register",{responseType:'text'})
       .subscribe(data=>{
       this.userId=data;
     })
  }
  getUserId():string{
    return this.userId ;
  }
  public getUser(userId: string): Observable<UserDto> {
    return this.httpClient.get<UserDto>("http://localhost:8080/api/user/" + userId);
  }
  unsubscribeToUser(userId: string):Observable<boolean> {
    return this.httpClient.post<boolean>("http://localhost:8080/api/user/unSubscribe/"+userId,null);

  }

  // subscribeToUser(userId: string): Observable<void> {
  //   return this.httpClient.post<void>(`${this.baseUrl}/${userId}/subscribe`, null);
  // }

  // unsubscribeFromUser(userId: string): Observable<void> {
  //   return this.httpClient.post<void>(`${this.baseUrl}/${userId}/unsubscribe`, null);
  // }

  addVideoToHistory(videoId: string): Observable<void> {
    return this.httpClient.post<void>(`${this.baseUrl}/history/${videoId}`, {});
  }

  getVideoHistory(): Observable<string[]> {
    return this.httpClient.get<string[]>(`${this.baseUrl}/history`);
  }

  getLikedVideos(): Observable<string[]> {
    return this.httpClient.get<string[]>(`${this.baseUrl}/favorite`);
  }

  subscribe(userId: string, targetUserId: string): Observable<any> {
    return this.httpClient.post(`${this.baseUrl}/${userId}/subscribe/${targetUserId}`, {});
  }

  unsubscribe(userId: string, targetUserId: string): Observable<any> {
    return this.httpClient.post(`${this.baseUrl}/${userId}/unsubscribe/${targetUserId}`, {});
  }

  getSubscribers(userId: string): Observable<string[]> {
    return this.httpClient.get<string[]>(`${this.baseUrl}/${userId}/subscribers`);
  }

  getSubscriptions(userId: string): Observable<string[]> {
    return this.httpClient.get<string[]>(`${this.baseUrl}/${userId}/subscriptions`);
  }

  getSuggestedVideos(userId: string): Observable<VideoDto[]> {
    return this.httpClient.get<VideoDto[]>(`${this.baseUrl}/suggested/${userId}`);
  }
}
