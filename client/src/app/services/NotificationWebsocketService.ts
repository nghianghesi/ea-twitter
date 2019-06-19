import { Injectable, OnInit, OnDestroy } from "@angular/core";
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import { Configurations } from '../commons/configurations';

import { authStore } from '../auth/auth-store';
import { IUserInfo } from '../auth/auth-state';

@Injectable({
  providedIn: 'root'
})
export class NotificationWebsocketService  implements OnInit, OnDestroy{
    private stompClient;
    private connected = false;
    private registeredJwt;
    private unsubscribeAuth; 
    private unsubscribeSocket;
      
  constructor(private config: Configurations) {                                 
    let socketurl = config.baseNotificationUrl + config.appName;
    let ws = new SockJS(socketurl);
    this.stompClient = Stomp.over(ws);
    this.stompClient.connect({}, (frame) => {
        console.log("socket connected");
        this.connected = true;
        this.registerJwt(authStore.getState().userinfo);                  
        this.subcribeForUpdates();
    });
  }

  private subcribeForUpdates():void{    
    if(this.unsubscribeSocket!=null){
        this.unsubscribeSocket();
        this.unsubscribeSocket = null;
    }  
    if(this.connected)  {
        this.unsubscribeSocket = this.stompClient.subscribe(this.config.tweetUpdatedQueue, 
        (msgOut) => {
            console.log(msgOut);
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