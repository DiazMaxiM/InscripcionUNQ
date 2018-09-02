import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';

import { AppComponent } from './app.component';

// Material angular
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModule } from './material.module';
import 'hammerjs';

import { SigninScreenComponent } from './auth/signin-screen.component';
import { PollScreenComponent } from './poll/poll-screen.component';
import { DataVerificationComponent } from './data-verification/data-verification-screen.component';
import { SelectSubjectsComponent } from './select-subjects/selectsubjects-screen.component';
import { RestService } from './rest.service';
import { PollService } from './poll/poll.service';

import { Routing} from './app.routing';
import { MomentModule } from 'ngx-moment';

import { CustomDialogComponent } from './custom-dialog/custom-dialog.component';
import { SubjectScreenComponent} from './subject/subject-screen.component';

@NgModule({
  declarations: [
    AppComponent,
    SigninScreenComponent,
    PollScreenComponent,
    DataVerificationComponent,
    CustomDialogComponent,
    SubjectScreenComponent,
    SelectSubjectsComponent
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
  providers: [RestService, PollService],
  bootstrap: [AppComponent],
  entryComponents: [CustomDialogComponent]
})
export class AppModule { }
