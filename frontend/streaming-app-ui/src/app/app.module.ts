import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {HttpClientModule} from "@angular/common/http";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {NgxFileDropModule} from "ngx-file-drop";
import { UploadVideoComponent } from './upload-video/upload-video.component';
import {MatButtonModule} from "@angular/material/button";
import { HeaderComponent } from './header/header.component';
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatIconModule} from "@angular/material/icon";
import { FooterComponent } from './footer/footer.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { SaveVideoDetailsComponent } from './save-video-details/save-video-details.component';
import {FlexLayoutModule} from "@angular/flex-layout";
import {MatInputModule} from "@angular/material/input";
import {MatSelectModule} from "@angular/material/select";


@NgModule({
  declarations: [
    AppComponent,
    UploadVideoComponent,
    HeaderComponent,
    FooterComponent,
    SaveVideoDetailsComponent,

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    FormsModule,
    HttpClientModule,
    NgxFileDropModule,
    MatButtonModule,
    MatToolbarModule,
    MatIconModule,
    NgbModule,
    FlexLayoutModule,
    MatInputModule,
    MatSelectModule,
    ReactiveFormsModule,

  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
