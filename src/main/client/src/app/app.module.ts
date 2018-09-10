import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {NavbarComponent} from './layout/navbar/navbar.component';
import {HomeComponent} from './home/home.component';
import {Globals} from "./globals";
import {WelcomeComponent} from './welcome/welcome.component';
import {LoginComponent} from './welcome/login/login.component';
import {AppRoutingModule} from "./app-routing.module";
import {AuthGuard} from "./shared/auth/auth.guard";
import {AuthService} from "./shared/auth/auth.service";
import {RegisterComponent} from './welcome/register/register.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    HomeComponent,
    WelcomeComponent,
    LoginComponent,
    RegisterComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    ReactiveFormsModule
  ],
  providers: [Globals, AuthGuard, AuthService],
  bootstrap: [AppComponent]
})
export class AppModule { }
