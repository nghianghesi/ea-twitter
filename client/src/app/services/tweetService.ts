import { Injectable } from "@angular/core";
import { HttpClient } from '@angular/common/http';

@Injectable({
    providedIn: 'root'
})
export class TweetService{
    constructor(private http:HttpClient){}

    tweet(tweet:string):Promise<any>{
        return this.http.post('<api>tweet',{tweet:tweet}).toPromise();
    }
}