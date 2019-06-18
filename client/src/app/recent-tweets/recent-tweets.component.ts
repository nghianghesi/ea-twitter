import { Component, OnInit } from '@angular/core';
import { TweetService } from '../services/tweetService';

@Component({
  selector: 'recent-tweets',
  templateUrl: './recent-tweets.component.html',
  styleUrls: ['./recent-tweets.component.css']
})
export class RecentTweetsComponent implements OnInit {

  tweets = [];
  constructor(private tweetService : TweetService) { 
    
  }


  onThumbUp(tweetid:number){
    this.tweetService.thumbup(tweetid);
  }  
  
  onThumbDown(tweetid:number){
    this.tweetService.thumbdown(tweetid);
  }  
  
  onRetweet(tweetid:number){
    this.tweetService.retweet(tweetid);
  }

  ngOnInit() {
    this.tweetService.getRecentTweets().then((res) =>{
      this.tweets = res;
    });
  }

}
