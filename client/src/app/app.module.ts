import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { UserComponent } from './user/user.component';
import { LoginFormComponent } from './login-form/login-form.component';
import { LoginStatusComponent } from './login-status/login-status.component';
import { LogoutComponent } from './logout/logout.component';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { ApiUrlInterceptor } from './commons/api-interceptor';
import { JwtConfig } from './auth/auth-jwt';
import { LoginService } from './services/loginService';
import { TweetService } from './services/tweetService';
import { Configurations } from './commons/configurations';
import { HeaderComponent } from './header/header.component';
import { JWTCanActivate } from './auth/jwt-can-activate';
import { ErrorService } from './services/errorService';
import { ErrorComponent } from './error/error.component';
import { JwtDirective } from './auth/jwt.directive';
import { BrowserAnimationsModule} from '@angular/platform-browser/animations';
import { MaterialModules } from './material-modules';
import { TwitterComponent } from './twitter/twitter.component';
import { TopTweetsComponent } from './top-tweets/top-tweets.component';
import { RecentTweetsComponent } from './recent-tweets/recent-tweets.component';
import { TweetFormComponent } from './tweet-form/tweet-form.component';
import { NotificationWebsocketService } from './services/NotificationWebsocketService';



@NgModule({
  declarations: [
    AppComponent,
    UserComponent,
    LoginFormComponent,
    LoginStatusComponent,
    LogoutComponent,
    HeaderComponent,
    HeaderComponent,
    ErrorComponent,
    JwtDirective,
    TwitterComponent,
    TopTweetsComponent,
    RecentTweetsComponent,
    TweetFormComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    HttpClientModule,
    JwtConfig,
    MaterialModules
  ],
  providers: [
    {provide:HTTP_INTERCEPTORS, useClass:ApiUrlInterceptor, multi:true},
    Configurations,
    LoginService,
    TweetService,
    NotificationWebsocketService,
    JWTCanActivate,
    ErrorService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
