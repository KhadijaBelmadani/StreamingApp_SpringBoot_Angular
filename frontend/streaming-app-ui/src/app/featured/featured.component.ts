import {Component, OnInit} from '@angular/core';
import {VideoService} from "../upload-video/video.service";
import {VideoDto} from "../video-dto";
import {UserService} from "../user.service";

@Component({
  selector: 'app-featured',
  templateUrl: './featured.component.html',
  styleUrls: ['./featured.component.css']
})
export class FeaturedComponent implements OnInit{
  featuredVideos:Array<VideoDto>=[];
  constructor(private videoService:VideoService,private userService:UserService) {
    this.userService.registerUser();
  }
  ngOnInit(): void {
    this.videoService.getAllVideos().subscribe(response => {
      this.featuredVideos=response;
    });
  }

}
