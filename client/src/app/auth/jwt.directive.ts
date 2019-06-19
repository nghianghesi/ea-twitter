import { Directive, OnInit, Input, OnDestroy, ElementRef, HostBinding } from '@angular/core';
import { authStore } from './auth-store';

@Directive({
  selector: '[jwt]'
})
export class JwtDirective implements OnInit, OnDestroy{

  @HostBinding('style.display') visibility;
  @Input('jwt') authorities:string|string[];
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
    if(authStore.getState().userinfo.id==''){
      return this.visibility='none';
    }
    if(this.authorities==null || this.authorities=='' || this.authorities.length==0){
      return this.visibility=this.el.nativeElement.previousvisibility;
    }
    let show = false;
    authStore.getState().userinfo.authorities.forEach((r) =>{
      if(!show){
        if(typeof this.authorities == 'string'){
          if(this.authorities == r){
            this.visibility=this.el.nativeElement.previousvisibility;
            show = true;
          }
        }else{
          if(this.authorities.indexOf(r)>=0){
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
