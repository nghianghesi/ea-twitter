export interface IUserInfo{
    jwt:string;
    userid:string,
    username:string,
    roles:string[],    
    photo?:string,
    exp:any
};

export interface IAuthState{
    userinfo:IUserInfo;
}