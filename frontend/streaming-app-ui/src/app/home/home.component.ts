import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {UserService} from "../user.service";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit{
  constructor(private router:Router) {
    this.router.navigateByUrl('/featured') ;

  }
  ngOnInit(): void {
  }

}
