import { Injectable } from "@angular/core";
import { Router } from '@angular/router';

@Injectable()
export class ErrorService{
    private _lastError = '';
    constructor(private router:Router){

    }

    navigateToError(error:string){
        this._lastError = error;
        this.router.navigate(['/error']);
    }

    get lastError():string{
        let res = this._lastError;
        this._lastError='';
        return res;
    }
}