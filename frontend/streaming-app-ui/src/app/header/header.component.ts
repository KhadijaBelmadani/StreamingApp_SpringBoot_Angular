import {Component, OnInit} from '@angular/core';
import {OidcSecurityService} from "angular-auth-oidc-client";
import {Router} from "@angular/router";
import {HttpClient} from "@angular/common/http";
import {VideoDto} from "../video-dto";
import {VideoService} from "../upload-video/video.service";


@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  searchTerm: string = '';
  searchQuery: string = '';
  videos: VideoDto[] = [];
  isAuthenticated:boolean=false;
  constructor(private oidcSecurityService: OidcSecurityService,private router:Router,private http:HttpClient,private videoService: VideoService) {
  }

  ngOnInit(): void {
    this.oidcSecurityService.isAuthenticated$.subscribe(({isAuthenticated}) => {
    this.isAuthenticated=isAuthenticated;

    });
  }

  login() {
    this.oidcSecurityService.authorize();
  }
  logout() {
    this.oidcSecurityService.logoffAndRevokeTokens();
    this.oidcSecurityService.logoffLocal();
  }

  uploadVideo() {
    // Check if the user is authenticated
    if (!this.isAuthenticated) {
      // User is not authenticated, display an alert
      alert('Please log in or sign up to upload a video.');


    } else {


      this.router.navigateByUrl('/upload-video');
    }
  }


  searchVideos() {
    if (this.searchTerm) {
      // Call your search service with the search term
      this.videoService.searchVideos(this.searchTerm)
        .subscribe(videos => {
          // Update your component's video list with search results
          this.videos = videos;
        });
    }
  }


}
