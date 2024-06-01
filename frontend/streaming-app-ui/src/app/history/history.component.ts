import {Component, OnInit} from '@angular/core';
import {UserService} from "../user.service";
import {VideoService} from "../upload-video/video.service";
import {VideoDto} from "../video-dto";

@Component({
  selector: 'app-history',
  templateUrl: './history.component.html',
  styleUrls: ['./history.component.css']
})
export class HistoryComponent implements OnInit{
  videoHistory: VideoDto[] = [];
  constructor(private userService: UserService,private videoService: VideoService) { }
  ngOnInit(): void {
    this.userService.getVideoHistory().subscribe(videoIds => {
      if (videoIds.length > 0) {
        this.videoService.getVideosByIds(videoIds).subscribe(videos => {
          this.videoHistory = videos;
        });
      }
    });
  }

}
