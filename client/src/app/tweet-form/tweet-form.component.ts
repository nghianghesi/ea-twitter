import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { TweetService } from '../services/tweetService';

@Component({
  selector: 'app-tweet-form',
  templateUrl: './tweet-form.component.html',
  styleUrls: ['./tweet-form.component.css']
})
export class TweetFormComponent implements OnInit {
    myform:FormGroup;
    tweetsucceeded = false;
    errors='';
    constructor(private fb:FormBuilder, 
      private tweetService:TweetService, private router: Router) {
      this.myform = this.fb.group({
        'tweet':['',Validators.required, Validators.maxLength(255)]
      });        
    }
  
    hasError(field, rule){
      return this.myform.get(field).hasError(rule)
            && this.myform.get(field).touched
    }
    
    onTweet ():void {
      if(this.myform.valid){
        this.tweetService.tweet(this.myform.controls['tweet'].value)
        .then((res:any)=>{
          if(res.id){
            this.tweetsucceeded = true;
          }else{
            this.errors = res.message;
          }
        });
      }
    }
  
    ngOnInit() {
    }
  
  }