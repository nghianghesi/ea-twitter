import { Injectable } from "@angular/core";

@Injectable()
export class Configurations{
    baseApiUrl = '';
    baseNotificationUrl = '';
    appName = 'eatwitter';
    tweetUpdatedQueue="/queue/tweetupdated"
    constructor(){
        const children = Array.prototype.slice.call(window.document.getElementsByTagName('apibase'));
        children.forEach((el) =>{
            this.baseApiUrl = el.getAttribute("href");
        });
        const notificationchild = Array.prototype.slice.call(window.document.getElementsByTagName('notificationbase'));
        notificationchild.forEach((el) =>{
            this.baseNotificationUrl = el.getAttribute("href");
        });
    }    
}