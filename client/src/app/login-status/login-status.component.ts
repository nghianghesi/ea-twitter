import { Component, OnInit, OnDestroy } from '@angular/core';
import { authStore } from '../auth/auth-store';

@Component({
  selector: 'app-login-status',
  templateUrl: './login-status.component.html',
  styleUrls: ['./login-status.component.css']
})
export class LoginStatusComponent implements OnInit, OnDestroy {

  userinfo;unsubscribe;

  constructor() {     
  }

  ngOnInit() {
    this.unsubscribe = authStore.subscribe(()=>{this.userinfo = authStore.getState().userinfo});
    this.userinfo = authStore.getState().userinfo;
  }

  ngOnDestroy(): void {
    this.unsubscribe();
  }
}
