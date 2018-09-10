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
import { SeleccionDeMateriasPorCursarComponent } from './seleccion-de-materias-por-cursar/seleccion-de-materias-por-cursar.component';
import { RestService } from './rest.service';
import { PollService } from './poll/poll.service';

import { Routing} from './app.routing';
import { MomentModule } from 'ngx-moment';

import { SubjectScreenComponent} from './subject/subject-screen.component';

import { SeleccionDeComisionDialogoComponent } from './seleccion-de-comision-dialogo/seleccion-de-comision-dialogo.component';
import { MatPaginatorI18n } from './spanish-paginator-intl';
import { MatPaginatorIntl } from '@angular/material';
import {RegistroDeComisionesSeleccionadasService} from './seleccion-de-materias-por-cursar/registro-de-comisiones-seleccionadas.service';

@NgModule({
  declarations: [
    AppComponent,
    SigninScreenComponent,
    PollScreenComponent,
    DataVerificationComponent,
    SubjectScreenComponent,
    SeleccionDeMateriasPorCursarComponent,
    SeleccionDeComisionDialogoComponent
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
  providers: [RestService, PollService, { provide: MatPaginatorIntl, useClass: MatPaginatorI18n }, RegistroDeComisionesSeleccionadasService],
  bootstrap: [AppComponent],
  entryComponents: [CustomDialogComponent, SeleccionDeComisionDialogoComponent]
})
export class AppModule { }
