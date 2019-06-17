import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { LoginService } from '../services/loginService';
import { authStore } from '../auth/auth-store';
import { JwtHelperService } from '@auth0/angular-jwt';
import { AuthActions } from '../auth/auth-actions';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.css']
})
export class LoginFormComponent implements OnInit {

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
