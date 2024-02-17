import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {VideoService} from "../services/video.service";

@Component({
  selector: 'app-video-detail',
  templateUrl: './video-detail.component.html',
  styleUrls: ['./video-detail.component.css']
})
export class VideoDetailComponent implements OnInit{
  videoId!: string;
  videoUrl!: string;


constructor(private activatedRoute: ActivatedRoute,
            private videoService:VideoService ) {

  // @ts-ignore
  this.videoId= this.activatedRoute.snapshot.params.videoId ;
  this.videoService.getVideo(this.videoId).subscribe(data=>{
    this.videoUrl =data.url;
  });

}

  ngOnInit(): void {
  }
}
