import {Component, OnInit} from '@angular/core';
import {VideoDto} from "../video-dto";
import {UserService} from "../user.service";
import {VideoService} from "../upload-video/video.service";

@Component({
  selector: 'app-liked-videos',
  templateUrl: './liked-videos.component.html',
  styleUrls: ['./liked-videos.component.css']
})
export class LikedVideosComponent implements OnInit{
  likedVideos: VideoDto[] = [];
  constructor(private userService: UserService,private videoService: VideoService) { }
  ngOnInit(): void {
    this.userService.getLikedVideos().subscribe(videoIds => {
      if (videoIds.length > 0) {
        this.videoService.getVideosByIds(videoIds).subscribe(videos => {
          this.likedVideos = videos;
        });
      }
    });
  }
}
