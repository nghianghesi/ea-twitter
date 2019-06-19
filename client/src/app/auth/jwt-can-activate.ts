import { Injectable } from "@angular/core";
import { Router, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { authStore } from '../auth/auth-store';
import { ErrorService } from '../services/errorService';

@Injectable()
export class JWTCanActivate{
    constructor(private router:Router, private errorHandler: ErrorService){

    }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        let authorities = (route.data["authorities"]) ? route.data["authorities"] : null;        
        if(authStore.getState().userinfo.id>''){
            if(authorities==null || authorities.length ==0){
                return true;
            }

            let granted = false;
            authStore.getState().userinfo.authorities.forEach(r=>{
                if(!granted){
                    if((typeof authorities == 'string' && authorities == r) 
                    || (typeof authorities != 'string' && authorities.indexOf(r)>=0)){
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