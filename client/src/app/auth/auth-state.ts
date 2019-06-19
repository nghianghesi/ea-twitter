export interface IUserInfo{
    jwt:string;
    id:string,
    username:string,
    authorities:string[],    
    photo?:string,
    exp:any
};

export interface IAuthState{
    userinfo:IUserInfo;
}