import { Component } from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-save-video-details',
  templateUrl: './save-video-details.component.html',
  styleUrls: ['./save-video-details.component.css']
})
export class SaveVideoDetailsComponent {
  saveVideoDetailsForm:FormGroup;
  title:FormControl=new FormControl('');
  description:FormControl=new FormControl('');
  videoStatus:FormControl=new FormControl('');
  constructor() {
    this.saveVideoDetailsForm=new FormGroup({
      title:this.title,
      description:this.description,
      videoStatus:this.videoStatus,
    })
  }

}
