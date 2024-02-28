import {Component} from '@angular/core';
import {FileSystemFileEntry, NgxFileDropEntry} from "ngx-file-drop";
// import {VideoService} from "./video.service";
import {Router} from "@angular/router";
import {VideoService} from "./video.service";

@Component({
  selector: 'app-upload-video',
  templateUrl: './upload-video.component.html',
  styleUrls: ['./upload-video.component.css']
})
export class UploadVideoComponent {

  public files: NgxFileDropEntry[] = [];
  fileUploaded: boolean = false;
  fileEntry: FileSystemFileEntry | undefined;

  constructor(private videoService: VideoService, private router: Router) {
  }

  public dropped(files: NgxFileDropEntry[]) {
    this.files = files;
    for (const droppedFile of files) {

      if (droppedFile.fileEntry.isFile) {
        this.fileEntry = droppedFile.fileEntry as FileSystemFileEntry;
        this.fileUploaded = true;
      }
    }
  }


  uploadVideo() {
    if (this.fileEntry !== undefined) {
      console.log(this.fileEntry)
      this.fileEntry.file(file => {
        this.videoService.uploadVideo(file).subscribe(data =>{
          console.log(data.videoId)
          this.router.navigateByUrl("/save-video-details/" + data.videoId);
        })
  })
}
  }
}
