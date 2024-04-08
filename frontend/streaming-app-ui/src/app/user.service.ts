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
}
