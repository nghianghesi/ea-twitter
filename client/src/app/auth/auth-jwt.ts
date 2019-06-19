import { JwtModule } from '@auth0/angular-jwt';

// export const JwtConfig = JwtModule.forRoot({
//   config: {
//     tokenGetter: () => {
//       return localStorage.getItem('access_token');
//     }
//   }
// });

export function jwtTokenGetter() {
  return localStorage.getItem('access_token');
}

export const JwtConfig = JwtModule.forRoot({
  config: {
    tokenGetter: jwtTokenGetter
  }
})