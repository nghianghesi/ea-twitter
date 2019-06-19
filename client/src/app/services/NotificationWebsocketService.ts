import { Injectable, OnInit, OnDestroy } from "@angular/core";
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import { Configurations } from '../commons/configurations';

import { authStore } from '../auth/auth-store';

@Injectable({
  providedIn: 'root'
})
export class NotificationWebsocketService  implements OnInit, OnDestroy{
    private stompClient;
    private connected = false;
    userinfo;unsubscribe; unsubscribesocket;
      
  constructor(private config: Configurations) {                                 
    let socketurl = config.baseNotificationUrl + "/secured/room";
    let ws = new SockJS(socketurl);
    this.stompClient = Stomp.over(ws);
    this.stompClient.connect({}, (frame) => {
        console.log("socket connected");
        this.connected = true;
        this.subcribeUpdates();
    });
  }

  private subcribeUpdates():void{    
    if(this.unsubscribesocket!=null){
        this.unsubscribesocket();
        this.unsubscribesocket = null;
    }  
    if(this.connected && this.userinfo.username > '')  {
        this.unsubscribesocket = this.stompClient.subscribe('user/'+this.userinfo.username+'/queue/', 
        (msgOut) => {
            console.log(msgOut);
        });
    }
  }

  ngOnInit() {
    this.unsubscribe = authStore.subscribe(()=>{
        this.userinfo = authStore.getState().userinfo;
        this.subcribeUpdates();
    });
    this.userinfo = authStore.getState().userinfo;
  }

  ngOnDestroy(): void {
    this.unsubscribe();
    if(this.unsubscribesocket!=null){
        this.unsubscribesocket();
        this.unsubscribesocket = null;
    }
  }

}