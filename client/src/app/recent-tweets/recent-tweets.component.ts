import { Component, OnInit, OnDestroy } from '@angular/core';
import { TweetService } from '../services/tweetService';
import { NotificationWebsocketService, TweetUpdatedData } from '../services/NotificationWebsocketService';
import { Configurations } from '../commons/configurations';

@Component({
  selector: 'recent-tweets',
  templateUrl: './recent-tweets.component.html',
  styleUrls: ['./recent-tweets.component.css']
})
export class RecentTweetsComponent implements OnInit, OnDestroy {
  tweets = [];
  private notificationSubcription;
  constructor(private tweetService : TweetService,private notification: NotificationWebsocketService ,private config: Configurations) { 
    
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

  ngOnDestroy(): void {
    this.notificationSubcription.unsubscribe();
  }
  
  ngOnInit() {
    this.tweetService.getRecentTweets().then((res) =>{
      this.tweets = res;
    });


    this.notificationSubcription = this.notification.TweetUpdatedSubject.subscribe({next:(updatedTweet:TweetUpdatedData) => {
      var maxDate;
      var hasTweetInlist = false;
      this.tweets.forEach(t=>{
        if(maxDate==null || maxDate<t.date){
          maxDate = t.date;
        }
        if(t.id == updatedTweet.id){
          t = updatedTweet.thumbStats;
          t = updatedTweet.retweetStats;
          hasTweetInlist=true;
        }
      });

      if(!hasTweetInlist && (maxDate==null || updatedTweet.date >= maxDate)){
        this.tweets.unshift(updatedTweet);
      }
      while(this.tweets.length > this.config.recentListSize){
        this.tweets.pop();
      }
    }
    });    
  }

}
