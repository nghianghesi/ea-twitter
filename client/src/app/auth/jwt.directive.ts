import { Directive, OnInit, Input, OnDestroy, ElementRef, HostBinding } from '@angular/core';
import { authStore } from './auth-store';

@Directive({
  selector: '[jwt]'
})
export class JwtDirective implements OnInit, OnDestroy{

  @HostBinding('style.display') visibility;
  @Input('jwt') roles:string|string[];
  unsubcribe;
  constructor(private el: ElementRef) { }

  ngOnInit(): void {    
    this.unsubcribe = authStore.subscribe(()=>{
      this.updateVisibility();
    });
    this.el.nativeElement.previousvisibility = this.visibility
    this.updateVisibility();
  }


  updateVisibility(){
    if(authStore.getState().userinfo.userid==''){
      return this.visibility='none';
    }
    if(this.roles==null || this.roles=='' || this.roles.length==0){
      return this.visibility=this.el.nativeElement.previousvisibility;
    }
    let show = false;
    authStore.getState().userinfo.roles.forEach((r) =>{
      if(!show){
        if(typeof this.roles == 'string'){
          if(this.roles == r){
            this.visibility=this.el.nativeElement.previousvisibility;
            show = true;
          }
        }else{
          if(this.roles.indexOf(r)>=0){
            this.visibility=this.el.nativeElement.previousvisibility;
            show = true;
          }
        }
      }
    })
    if(!show){
      this.visibility='none';
    }
  }

  ngOnDestroy(): void {
    this.unsubcribe();
  }
}
