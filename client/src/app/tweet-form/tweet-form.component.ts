import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-tweet-form',
  templateUrl: './tweet-form.component.html',
  styleUrls: ['./tweet-form.component.css']
})
export class TweetFormComponent implements OnInit {
    myform:FormGroup;
    loginsucceeded = false;
    errors='';
    constructor(private fb:FormBuilder, private loginService:LoginService,private jwtHelper: JwtHelperService, private router: Router) {
      this.myform = this.fb.group({
        'username':['',Validators.required],
        'password':['',Validators.required]
      });        
    }
  
    hasError(field, rule){
      return this.myform.get(field).hasError(rule)
            && this.myform.get(field).touched
    }
    
    onLogin ():void {
      if(this.myform.valid){
        this.loginService.doLogin(
          this.myform.controls['username'].value,
          this.myform.controls['password'].value)
        .then((res:any)=>{
          if(res.accessToken){
            let userinfo = res.userPrincipal;
            userinfo.jwt = res.accessToken;
            userinfo.jwtType = res.tokenType;
            authStore.dispatch(AuthActions.login(userinfo));
            this.errors = '';
            this.loginsucceeded = true;
            this.router.navigate(['/']);
          }else{
            this.errors = res.message;
          }
        });
      }
    }
  
    ngOnInit() {
    }
  
  }