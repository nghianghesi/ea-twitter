import { Injectable } from "@angular/core";
import { Router, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { authStore } from '../auth/auth-store';
import { ErrorService } from '../services/errorService';

@Injectable()
export class JWTCanActivate{
    constructor(private router:Router, private errorHandler: ErrorService){

    }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        let roles = (route.data["roles"]) ? route.data["roles"] : null;        
        if(authStore.getState().userinfo.userid>''){
            if(roles==null || roles.length ==0){
                return true;
            }

            let granted = false;
            authStore.getState().userinfo.roles.forEach(r=>{
                if(!granted){
                    if((typeof roles == 'string' && roles == r) 
                    || (typeof roles != 'string' && roles.indexOf(r)>=0)){
                        granted = true;
                    }
                }
            });
            if(!granted){
                this.errorHandler.navigateToError("Acces denied");   
            }
            return granted;
        }else{
            this.router.navigate(['/login'], { queryParams: { returnUrl: state.url }});        
            return false;
        }
      }
    
}