import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

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

import { FeedbackUsuarioDialogoComponent } from './feedback-usuario-dialogo/feedback-usuario-dialogo.component';
import { SubjectScreenComponent} from './subject/subject-screen.component';

import { SeleccionDeComisionDialogoComponent } from './seleccion-de-comision-dialogo/seleccion-de-comision-dialogo.component';
import { MatPaginatorI18n } from './spanish-paginator-intl';
import { MatPaginatorIntl } from '@angular/material';
import {RegistroDeComisionesSeleccionadasService} from './seleccion-de-materias-por-cursar/registro-de-comisiones-seleccionadas.service';

import { UtilesService } from './utiles.service';
import {HttpClientModule} from '@angular/common/http';
import {EncuestaFinalizadaComponent } from './encuesta-finalizada/encuesta-finalizada.component';
import { LoginAdminScreenComponent } from './login-admin/loginadmin-screen.component';
import { TareasAdminScreenComponent } from './tareas-admin/tareasadmin-screen.component';

@NgModule({
  declarations: [
    AppComponent,
    SigninScreenComponent,
    PollScreenComponent,
    DataVerificationComponent,
    FeedbackUsuarioDialogoComponent,
    SubjectScreenComponent,
    SeleccionDeMateriasPorCursarComponent,
    SeleccionDeComisionDialogoComponent,
    EncuestaFinalizadaComponent,
    LoginAdminScreenComponent,
    TareasAdminScreenComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
    Routing,
    MomentModule,
    HttpClientModule
  ],
  providers: [RestService, PollService, { provide: MatPaginatorIntl, useClass: MatPaginatorI18n }, RegistroDeComisionesSeleccionadasService, UtilesService],
  bootstrap: [AppComponent],
  entryComponents: [FeedbackUsuarioDialogoComponent, SeleccionDeComisionDialogoComponent]
})
export class AppModule { }
