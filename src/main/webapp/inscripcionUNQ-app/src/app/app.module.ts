import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AppComponent } from './app.component';

// Material angular
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModule } from './material.module';
import 'hammerjs';

import { LoginEstudianteComponent } from './autenticacion/login-estudiante.component';
import { InformacionEstudianteComponent } from './informacion-del-usuario/informacion-estudiante.component';
import { SeleccionDeMateriasPorCursarComponent } from './seleccion-de-materias-por-cursar/seleccion-de-materias-por-cursar.component';
import { RestService } from './rest.service';

import { AppRoutingModule} from './app.routing';
import { MomentModule } from 'ngx-moment';

import { FeedbackUsuarioDialogoComponent } from './feedback-usuario-dialogo/feedback-usuario-dialogo.component';
import { MateriasAprobadasComponent } from './materias-aprobadas/materias-aprobadas.component';

import { SeleccionDeComisionDialogoComponent } from './seleccion-de-comision-dialogo/seleccion-de-comision-dialogo.component';
import { MatPaginatorI18n } from './spanish-paginator-intl';
import { MatPaginatorIntl } from '@angular/material';
import { RegistroDeComisionesSeleccionadasService} from './seleccion-de-materias-por-cursar/registro-de-comisiones-seleccionadas.service';

import { UtilesService } from './utiles.service';
import { HttpClientModule} from '@angular/common/http';
import { EncuestaFinalizadaComponent } from './encuesta-finalizada/encuesta-finalizada.component';
import { LoginUsuarioComponent } from './autenticacion/login-usuario.component';
import { TareasUsuarioComponent } from './tareas-usuario/tareas-usuario.component';
import { EncuestasDisponiblesComponent } from './encuestas-disponibles/encuestas-disponibles.component';
import { CarrerasComponent } from './carreras/carreras.component';
import { MateriasComponent } from './materias/materias.component';
import { CarreraDialogoComponent } from './carrera-dialogo/carrera-dialogo.component';
import { ofertaAcademicaComponent } from './oferta-academica/oferta-academica.component';
import { OfertaAcademicaDialogoComponent } from './oferta-academica-dialogo/oferta-academica.dialogo.component';
import { ModificacionDeMateriaDialogoComponent } from './modificacion-de-materia-dialogo/modificacion-de-materia-dialogo.component';
import { ComisionesDeOfertaComponent } from './comisiones-de-oferta/comisiones-de-oferta.component';
import { filtroBusquedaPipe } from './filtro-busqueda.pipe';
import { PeriodoComponent } from './periodos/periodo.component';
import { PeriodoDialogoComponent } from './periodo-dialogo/periodo-dialogo.component';
import { IncidenciaDialogoComponent } from './incidencia-dialogo/incidencia-dialogo.component';
import { ComisionesComponent } from './comisiones/comisiones.component';
import { ComisionDialogoComponent } from './comision-dialogo/comision-dialogo.component';
import { NgbModule} from '@ng-bootstrap/ng-bootstrap';
import { ComisionesDeOfertaDialogoComponent } from './comisiones-de-oferta-dialogo/comisiones-de-oferta-dialogo.component';
import { EquivalenciasComponent } from './equivalencias/equivalencias.component';
import { EquivalenciaDialogoComponent } from './equivalencia-dialogo/equivalencia-dialogo.component';
import { UsuariosComponent } from './usuarios/usuarios.component';
import { IncidenciasComponent } from './incidencias/incidencias.component';
import { ModificacionDeIncidenciaDialogoComponent } from './modificacion-de-incidencia-dialogo/modificacion-de-incidencia-dialogo.component';
import { EdicionUsuarioDialogoComponent } from './edicion-usuario-dialogo/edicion-usuario-dialogo.component';
import { AltaUsuarioDialogoComponent } from './alta-usuario-dialogo/alta-usuario-dialogo.component';
import { usuarioLogueadoService } from './usuario-logueado.service';

@NgModule({
  declarations: [
    AppComponent,
    LoginEstudianteComponent,
    InformacionEstudianteComponent,
    FeedbackUsuarioDialogoComponent,
    SeleccionDeMateriasPorCursarComponent,
    SeleccionDeComisionDialogoComponent,
    EncuestaFinalizadaComponent,
    LoginUsuarioComponent,
    TareasUsuarioComponent,
    EncuestasDisponiblesComponent,
    MateriasAprobadasComponent,
    CarrerasComponent,
    MateriasComponent,
    CarreraDialogoComponent,
    ofertaAcademicaComponent,
    OfertaAcademicaDialogoComponent,
    ModificacionDeMateriaDialogoComponent,
    ComisionesDeOfertaComponent,
    filtroBusquedaPipe,
    PeriodoComponent,
    PeriodoDialogoComponent,
    IncidenciaDialogoComponent,
    ComisionesComponent,
    ComisionDialogoComponent,
    ComisionesDeOfertaDialogoComponent,
    EquivalenciasComponent,
    EquivalenciaDialogoComponent,
    UsuariosComponent,
    IncidenciasComponent,
    ModificacionDeIncidenciaDialogoComponent,
    EdicionUsuarioDialogoComponent,
    AltaUsuarioDialogoComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
    AppRoutingModule,
    MomentModule,
    HttpClientModule,
    NgbModule
  ],
  providers: [RestService, { provide: MatPaginatorIntl, useClass: MatPaginatorI18n }, RegistroDeComisionesSeleccionadasService,
    UtilesService, usuarioLogueadoService],
  bootstrap: [AppComponent],
  entryComponents: [FeedbackUsuarioDialogoComponent, SeleccionDeComisionDialogoComponent,
  CarreraDialogoComponent, OfertaAcademicaDialogoComponent, ModificacionDeMateriaDialogoComponent,
  IncidenciaDialogoComponent, ComisionDialogoComponent, PeriodoDialogoComponent, ComisionesDeOfertaDialogoComponent,
  EquivalenciaDialogoComponent, ModificacionDeIncidenciaDialogoComponent,
  EdicionUsuarioDialogoComponent, AltaUsuarioDialogoComponent]
})
export class AppModule { }
