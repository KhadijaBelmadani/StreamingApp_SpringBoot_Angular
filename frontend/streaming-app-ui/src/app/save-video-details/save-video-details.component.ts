import {Component, inject} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {MatChipEditedEvent, MatChipInputEvent} from "@angular/material/chips";
import {LiveAnnouncer} from "@angular/cdk/a11y";
import {COMMA, ENTER} from "@angular/cdk/keycodes";
import {ActivatedRoute, Router} from "@angular/router";
import {VideoService} from "../upload-video/video.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import {VideoDto} from "../video-dto";
import {UserService} from "../user.service";

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


  addOnBlur = true;
  readonly separatorKeysCodes = [ENTER, COMMA] as const;
  tags: string[] = [];
  selectedFile!:File;
  selectedFileName='';
  videoId='';
  fileSelected=false;
  videoUrl!:string;
  thumbnailUrl!:string;
  userId!:string

  announcer = inject(LiveAnnouncer);

  constructor(private activatedRoute:ActivatedRoute,private videoService:VideoService,
              private matSnackBar:MatSnackBar,private userService:UserService,private router:Router) {



    this.videoId= this.activatedRoute.snapshot.params['videoId'] ;
    this.videoService.getVideo(this.videoId).subscribe(data=>{
       this.videoUrl =data.url;
       this.thumbnailUrl=data.thumbnailUrl;

    // this.userService.getUser(this.userId).subscribe(data=>{
    //   // @ts-ignore
    //   this.userId=data.id;
    // })
    });
    this.saveVideoDetailsForm=new FormGroup({
      title:this.title,
      description:this.description,
      videoStatus:this.videoStatus,
    })
  }

  add(event: MatChipInputEvent): void {
    const value = (event.value || '').trim();

    // Add our fruit
    if (value) {
      this.tags.push(value);
    }

    // Clear the input value
    event.chipInput!.clear();
  }




  remove(value: string): void {
    const index = this.tags.indexOf(value);

    if (index >= 0) {
      this.tags.splice(index, 1);

      this.announcer.announce(`Removed ${value}`);
    }
  }

  onFileSelected($event: Event) {
    // @ts-ignore
    this.selectedFile = $event.target.files[0];
    this.selectedFileName=this.selectedFile.name;
    this.fileSelected=true;
  }

  onUpload() {
    this.videoService.uploadThumbnail(this.selectedFile,this.videoId)
      .subscribe(data=>{
        console.log(data);

        //show an upload success notification
        this.matSnackBar.open("thumbnail Uploaded Successfully","Ok");
      })
  }

  saveVideo() {
    //Call the video service to make http call to our backend
    const videoMetaData:VideoDto={
      "id":this.videoId,
      "title":this.saveVideoDetailsForm.get('title')?.value,
      "description":this.saveVideoDetailsForm.get('description')?.value,
      "tags":this.tags,
      "videoStatus":this.saveVideoDetailsForm.get('videoStatus')?.value,
      "url":this.videoUrl,
      "thumbnailUrl":this.thumbnailUrl,
      "likeCount":0,
      "dislikeCount":0,
      "viewCount":0,
      "userId":this.userId,
    }

    this.videoService.saveVideo(videoMetaData).subscribe(data=>{
      this.matSnackBar.open("Video MetaData Updated successfully","OK");
      this.router.navigate(['/video-details', this.videoId]);

    })
  }

}
