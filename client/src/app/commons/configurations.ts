import { Injectable } from "@angular/core";

@Injectable()
export class Configurations{
    baseApiUrl = ''
    constructor(){
        const children = Array.prototype.slice.call(window.document.getElementsByTagName('apibase'));
        children.forEach((el) =>{
            this.baseApiUrl = el.getAttribute("href");
        });
    }    
}