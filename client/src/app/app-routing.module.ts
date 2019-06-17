import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginFormComponent } from './login-form/login-form.component';
import { LogoutComponent } from './logout/logout.component';
import { TwitterComponent } from './twitter/twitter.component';
import { ErrorComponent } from './error/error.component';

const routes: Routes = [
  {path:'error', component:ErrorComponent},
  {path:'login', component:LoginFormComponent},
  {path:'logout', component:LogoutComponent},
  {path:'twitter', component:TwitterComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
