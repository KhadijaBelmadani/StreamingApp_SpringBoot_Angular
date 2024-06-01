import {Component, OnInit} from '@angular/core';
import {VideoDto} from "../video-dto";
import {UserService} from "../user.service";
import {VideoService} from "../upload-video/video.service";

@Component({
  selector: 'app-recommendations',
  templateUrl: './recommendations.component.html',
  styleUrls: ['./recommendations.component.css']
})
export class RecommendationsComponent implements OnInit{
  videoHistory: VideoDto[] = [];
  likedVideos: VideoDto[] = [];
  constructor(private userService: UserService,private videoService: VideoService) { }
  ngOnInit(): void {
    this.userService.getVideoHistory().subscribe(videoIds => {
      if (videoIds.length > 0) {
        this.videoService.getVideosByIds(videoIds).subscribe(videos => {
          this.videoHistory = videos;
          this.userService.getLikedVideos().subscribe(videoIds => {
            if (videoIds.length > 0) {
              this.videoService.getVideosByIds(videoIds).subscribe(videos => {
                this.likedVideos = videos;
              });
            }
          });
        });
      }
    });
  }

}
