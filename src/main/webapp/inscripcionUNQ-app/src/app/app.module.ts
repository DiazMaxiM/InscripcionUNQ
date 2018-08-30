import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';

import { AppComponent } from './app.component';

// Material angular
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MaterialModule} from './material.module';
import 'hammerjs';

import { SigninScreenComponent } from './auth/signin-screen.component';
import {PollScreenComponent} from './student-poll/poll-screen.component';

import { AuthService } from './auth/auth.service';
import {PollService} from "./student-poll/poll.service";

import {Routing} from './app.routing';
import { MomentModule } from 'ngx-moment';


@NgModule({
  declarations: [
    AppComponent,
    SigninScreenComponent,
    PollScreenComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
    HttpModule,
    Routing,
    MomentModule
  ],
  providers: [AuthService,PollService],
  bootstrap: [AppComponent]
})
export class AppModule { }
