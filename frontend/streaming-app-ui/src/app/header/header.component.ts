import {Component, OnInit} from '@angular/core';
import {OidcSecurityService} from "angular-auth-oidc-client";
import {Router} from "@angular/router";


@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  isAuthenticated:boolean=false;
  constructor(private oidcSecurityService: OidcSecurityService,private router:Router) {
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
}
