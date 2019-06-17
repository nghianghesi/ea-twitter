import { Injectable } from "@angular/core";
import { HttpClient } from '@angular/common/http';

@Injectable({
    providedIn: 'root'
})
export class LoginService{
    constructor(private http:HttpClient){}

    doLogin(username:string, password:string):Promise<any>{
        return this.http.post('<api>auth/login',{usernameOrEmail:username, password:password})
        .toPromise();
    }

    doSignup(user:any):Promise<any>{
        return this.http.post('<api>login/signup',user).toPromise();
    }  
    
    checkUniqueUsername(username:string):Promise<any>{
        return this.http.post('<api>login/checkusername',{username:username}).toPromise();
    }
}