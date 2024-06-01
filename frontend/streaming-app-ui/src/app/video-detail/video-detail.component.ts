import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {VideoService} from "../upload-video/video.service";
import {UserService} from "../user.service";
import {VideoDto} from "../video-dto";


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
  likeCount!: number;
  dislikeCount!: number;
  viewCount: number=0;
  showSubscribeButton:boolean=true;
  showUnsubscribeButton:boolean=false;
  // followerCount: number = 0;
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
    // this.loadVideoDetails();
  }

  likeVideo() {
    this.videoService.likeVideo(this.videoId).subscribe((data: VideoDto) => {
      this.likeCount = data.likeCount;
      this.dislikeCount = data.dislikeCount;
    });
  }

  dislikeVideo() {
    this.videoService.dislikeVideo(this.videoId).subscribe((data: VideoDto) => {
      this.likeCount = data.likeCount;
      this.dislikeCount = data.dislikeCount;
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
    let userId = this.userService.getUserId();
    this.userService.unsubscribeToUser(userId)
      .subscribe(data => {
        this.showUnsubscribeButton = false;
        this.showSubscribeButton = true;
      });
    // }

    // private loadVideoDetails() {
    //   this.videoService.getVideoDetails(this.videoId).subscribe((data: VideoDto) => {
    //     this.likeCount = data.likeCount;
    //     this.dislikeCount = data.dislikeCount;
    //     // this.followerCount = data.followerCount;
    //     // this.showSubscribeButton = !data.isSubscribed;
    //     // this.showUnsubscribeButton = data.isSubscribed;
    //   });
    // }

    // subscribeToUser() {
    //   let userId = this.userService.getUserId();
    //   this.userService.subscribeToUser(userId).subscribe(() => {
    //     this.showUnsubscribeButton = true;
    //     this.showSubscribeButton = false;
    //     this.followerCount++;
    //   });
    // }
    //
    // unsubscribeToUser() {
    //   let userId = this.userService.getUserId();
    //   this.userService.unsubscribeFromUser(userId).subscribe(() => {
    //     this.showUnsubscribeButton = false;
    //     this.showSubscribeButton = true;
    //     this.followerCount--;
    //   });
    // }
  }}
