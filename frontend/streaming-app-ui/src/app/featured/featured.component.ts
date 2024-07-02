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
  // titles = ['Creativity', 'Esport', 'Gaming', 'Lifestyle', 'Songs'];
  // groupedFeaturedVideos: Array<VideoDto[]> = [];

  constructor(private videoService:VideoService,private userService:UserService) {
    this.userService.registerUser();
  }
  ngOnInit(): void {
    // this.groupFeaturedVideos();

    this.videoService.getAllVideos().subscribe(response => {
      this.featuredVideos=response;
    });
  }

  // groupFeaturedVideos() {
  //   for (let i = 0; i < this.featuredVideos.length; i += 3) {
  //     this.groupedFeaturedVideos.push(this.featuredVideos.slice(i, i + 3));
  //   }
  // }

}
