import { IUserInfo } from './auth-state';

export const LOG_IN= 'LOG_IN';
export const LOG_OUT = 'LOG_OUT'

export const AuthActions = {
    login:function(userinfo:IUserInfo){
        return {
            type:LOG_IN,
            payload:userinfo
        }
    },
    logout: function (){
        return {
            type:LOG_OUT
        }
    }
}