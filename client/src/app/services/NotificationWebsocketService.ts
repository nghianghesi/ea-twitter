import { Injectable, OnInit, OnDestroy } from "@angular/core";
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import { Configurations } from '../commons/configurations';

import { authStore } from '../auth/auth-store';
import { IUserInfo } from '../auth/auth-state';
import { Subject } from 'rxjs';

export interface  TweetUpdatedData{
  id:number;	
	tweet:String;
	date:Date;	
	byUser:String;		
	thumbStats:number;
	retweetStats:number;
	thumbtype:String;
	isRetweeted:boolean;
}

@Injectable({
  providedIn: 'root'
})
export class NotificationWebsocketService  implements OnInit, OnDestroy{
    private stompClient;
    private registeredJwt;
    private unsubscribeAuth; 
    private unsubscribeSocket;
    private sessionId;
    private tweetUpdatedSubject = new Subject<TweetUpdatedData>();
    public get TweetUpdatedSubject(){
      return this.tweetUpdatedSubject;
    }
  constructor(private config: Configurations) {                                 
    let socketurl = config.baseNotificationUrl + config.appName;
    let ws = new SockJS(socketurl);
    this.stompClient = Stomp.over(ws);
    this.stompClient.connect({}, (frame) => {
        console.log("socket connected ");
        let urlSplits = ws._transport.url.split('/');
        if(urlSplits.length>2){
          this.sessionId = urlSplits[urlSplits.length-2];
          this.registerJwt(authStore.getState().userinfo);                  
          this.subcribeForUpdates();
        }
    });
  }

  private subcribeForUpdates():void{    
    if(this.unsubscribeSocket!=null){
        this.unsubscribeSocket();
        this.unsubscribeSocket = null;
    }  
    if(this.sessionId>'')  {
        this.unsubscribeSocket = this.stompClient.subscribe(this.config.tweetUpdatedQueue+"/"+this.sessionId, 
        (msgOut) => {
            console.log(msgOut);
            this.tweetUpdatedSubject.next(JSON.parse(msgOut.body))            
        });
    }
  }

  private registerJwt(userinfo:IUserInfo){
    if(this.registeredJwt > ''){
      this.stompClient.send("/"+this.config.appName+"/unregister",{}, this.registeredJwt);      
    }
    if(userinfo!=null && userinfo.jwt>''){
      this.stompClient.send("/"+this.config.appName+"/register",{}, userinfo.jwt);   
      this.registeredJwt=userinfo.jwt; 
    }
  }

  ngOnInit() {
    this.unsubscribeAuth = authStore.subscribe(()=>{
        this.registerJwt(authStore.getState().userinfo);
    });
  }

  ngOnDestroy(): void {
    this.unsubscribeAuth();
    if(this.unsubscribeSocket!=null){
        this.unsubscribeSocket();
        this.unsubscribeSocket = null;
    }
  }

}