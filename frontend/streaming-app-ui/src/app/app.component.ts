import {Component, HostBinding, OnInit} from '@angular/core';
import {LoginResponse, OidcSecurityService} from "angular-auth-oidc-client";
import {BreakpointObserver, Breakpoints} from "@angular/cdk/layout";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  @HostBinding('class.pc') pcMode=false;
  title = 'streaming-app-ui';

constructor(private oidcSecurityService:OidcSecurityService ,private breakPointObserver:BreakpointObserver) {
}
  ngOnInit():void {
    this.oidcSecurityService
      .checkAuth()
      .subscribe(({isAuthenticated})=>{
        console.log('app is authenticated',isAuthenticated);
    });
    this.breakPointObserver.observe([Breakpoints.HandsetPortrait,Breakpoints.WebLandscape])
      .subscribe({
        next:(result: any)=>{
          for(let breakpoint of Object.keys(result.breakpoints)){
            if(result.breakpoints[breakpoint]){
              if(breakpoint==Breakpoints.HandsetPortrait){
                this.pcMode=false;
              }
              if(breakpoint==Breakpoints.WebLandscape){
                this.pcMode=true;
              }
            }
          }
        },
      });

  }
}
