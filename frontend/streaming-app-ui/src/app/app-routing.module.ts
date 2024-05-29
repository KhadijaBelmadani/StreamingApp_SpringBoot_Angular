import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {UploadVideoComponent} from "./upload-video/upload-video.component";
import {SaveVideoDetailsComponent} from "./save-video-details/save-video-details.component";
import {VideoDetailComponent} from "./video-detail/video-detail.component";
import {HomeComponent} from "./home/home.component";
import {SubscriptionsComponent} from "./subscriptions/subscriptions.component";
import {HistoryComponent} from "./history/history.component";
import {LikedVideosComponent} from "./liked-videos/liked-videos.component";
import {RecommendationsComponent} from "./recommendations/recommendations.component";
import {FeaturedComponent} from "./featured/featured.component";
import {CallbackComponent} from "./callback/callback.component";
import {HeaderComponent} from "./header/header.component";
import {SearchComponent} from "./search/search.component";
import {LiveRoomComponent} from "./live-room/live-room.component";
import {ProfileComponent} from "./profile/profile.component";

const routes: Routes = [
  {
    path:'upload-video',component:UploadVideoComponent
  },
  {
    path:'save-video-details/:videoId',component:SaveVideoDetailsComponent

  },
  {
    path:'video-details/:videoId',component:VideoDetailComponent

  },
  {
    path:'search',component:SearchComponent
  },
  {
    path:'callback',component:CallbackComponent

  },
  {
    path:'go-live',component:LiveRoomComponent
  },
  {
    path:'goProfile' ,component:ProfileComponent
  }
,
  {
    path:'',component:HomeComponent,
    children:[
      {
        path:'subscriptions',component:SubscriptionsComponent

      },
      {
        path:'history',component:HistoryComponent

      },
      {
        path:'liked-videos',component:LikedVideosComponent

      },
      {
        path:'recommendations',component:RecommendationsComponent
      },
      {
        path:'featured',component:FeaturedComponent
      }
    ]
  },


];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
