export interface VideoDto {
  id:string;
  description:string;
  title:string;
  tags:Array<string>;
  videoStatus:string;
  url:string;
  thumbnailUrl:string;
  likeCount: number;
  dislikeCount:number;
  viewCount:number;
  userId:string;
}
