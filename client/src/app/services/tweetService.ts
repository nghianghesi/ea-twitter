import { Injectable } from "@angular/core";
import { HttpClient } from '@angular/common/http';

@Injectable({
    providedIn: 'root'
})
export class TweetService{
    constructor(private http:HttpClient){}

    getRecentTweets():Promise<any>{
        return this.http.get('<api>tweet/recent').toPromise();
    }   

    getHotTweets():Promise<any>{
        return this.http.get('<api>tweet/hot').toPromise();
    }     
    
    tweet(tweet:string):Promise<any>{
        return this.http.post('<api>tweet',{tweet:tweet}).toPromise();
    }   
    
    retweet(tweet_id:number):Promise<any>{
        return this.http.post('<api>tweet/retweet',{tweet_id:tweet_id}).toPromise();
    } 
    
    thumbup(tweet_id:number):Promise<any>{
        return this.http.post('<api>tweet/thumb',{tweet_id:tweet_id, type:'Up'}).toPromise();
    }   
    
    thumbdown(tweet_id:number):Promise<any>{
        return this.http.post('<api>tweet/thumb',{tweet_id:tweet_id, type:'Down'}).toPromise();
    }
}