import {Component, OnInit} from '@angular/core';
import {VideoDto} from "../video-dto";
import {VideoService} from "../upload-video/video.service";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit{
  searchResults: any[] = [];

  constructor(private route: ActivatedRoute, private videoService: VideoService) {}



  ngOnInit(): void {

    this.route.queryParams.subscribe(params => {
      const query = params['query'];
      this.videoService.searchVideos(query).subscribe(results => {
        this.searchResults = results;}

        );
    });

  }
}
