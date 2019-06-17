import { IAuthState } from "./auth-state";
import { LOG_IN, LOG_OUT } from './auth-actions';
import * as moment from 'moment';

let initialState : IAuthState = JSON.parse(localStorage.getItem('authstate'));

if(initialState!=null){
    initialState.userinfo.exp = moment(initialState.userinfo.exp);
}

if(initialState==null || initialState.userinfo.exp < moment()){
    initialState={
        userinfo:{
            userid:'',
            jwt:'',
            username:'',
            roles:[],
            exp: moment().add(20,'minute')
        }
    }    
}

function updateLocalToken(state){
    localStorage.setItem('access_token', state.userinfo.jwt);
    localStorage.setItem('authstate', JSON.stringify(state));
}

function login(state,action):IAuthState{
    const newstate = Object.assign({}, state, {userinfo:action.payload})
    updateLocalToken(newstate);
    return newstate;
}

function logout(state, action):IAuthState{
    const newstate = Object.assign({}, state, {userinfo:{userid:'', username:'', roles:[], jwt:''}})    
    updateLocalToken(newstate);
    return newstate;
}

export function reducer(state: IAuthState = initialState, action){
    switch(action.type){
        case LOG_IN: return login(state, action);
        case LOG_OUT: return logout(state, action);
        default: return initialState;
    }
}