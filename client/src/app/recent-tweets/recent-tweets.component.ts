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

  ngOnInit() {
    this.tweetService.getRecentTweets().then((res) =>{
      this.tweets = res;
    });
  }

}
