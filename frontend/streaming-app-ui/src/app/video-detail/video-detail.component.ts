import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {VideoService} from "../upload-video/video.service";
import {UserService} from "../user.service";


@Component({
  selector: 'app-video-detail',
  templateUrl: './video-detail.component.html',
  styleUrls: ['./video-detail.component.css']
})
export class VideoDetailComponent implements OnInit{
  videoId!: string;
  videoUrl!: string;
  videoAvailable: boolean=false;
  videoTitle!: string;
  videoDescription!: string;
  videoTags: Array<string>=[];
  likeCount: number=0;
  dislikeCount: number=0;
  viewCount: number=0;
  showSubscribeButton:boolean=true;
  showUnsubscribeButton:boolean=false;

  uploaderFullName!: string;



  constructor(private activatedRoute: ActivatedRoute,
            private videoService:VideoService,
            private userService:UserService) {

  this.videoId= this.activatedRoute.snapshot.params['videoId'] ;
  this.videoService.getVideo(this.videoId).subscribe(data=>{
    console.log('Video Data:', data);
    this.videoUrl =data.url;
    this.videoTitle=data.title;
    this.videoDescription=data.description;
    this.videoTags=data.tags;
    this.videoAvailable=true;
    this.likeCount=data.likeCount;
    this.dislikeCount=data.dislikeCount;
    this.viewCount=data.viewCount;
    this.userService.getUser(data.userId).subscribe(user => {
      this.uploaderFullName = user.givenName;
    });
  });


}

  ngOnInit(): void {
    this.userService.registerUser();
  }

  likeVideo() {
    this.videoService.likeVideo(this.videoId).subscribe(data=>{
      this.likeCount=data.likeCount;
      this.dislikeCount=data.dislikeCount;
    });
  }

  disLike() {
    this.videoService.disLikeVideo(this.videoId).subscribe(data=>{
      this.likeCount=data.likeCount;
      this.dislikeCount=data.dislikeCount;
    });
  }

  subscribeToUser() {
    let userId=this.userService.getUserId();
    this.userService.subscribeToUser(userId)
      .subscribe(data=>{
          this.showUnsubscribeButton=true;
          this.showSubscribeButton=false;
      });
  }

  unsubscribeToUser() {
    let userId=this.userService.getUserId();
    this.userService.unsubscribeToUser(userId)
      .subscribe(data=>{
        this.showUnsubscribeButton=false;
        this.showSubscribeButton=true;
      });
  }
}
