import { Component, OnInit, OnDestroy } from '@angular/core';
import { authStore } from '../auth/auth-store';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styles: []
})
export class HeaderComponent implements OnInit, OnDestroy {

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
