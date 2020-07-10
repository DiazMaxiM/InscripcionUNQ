import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SeleccionDeMateriasPorCursarComponent } from './seleccion-de-materias-por-cursar/seleccion-de-materias-por-cursar.component';
import { EncuestaFinalizadaComponent } from './encuesta-finalizada/encuesta-finalizada.component';
import { TareasUsuarioComponent } from './tareas-usuario/tareas-usuario.component';
import { LoginComponent } from './autenticacion/login.component';
import { InformacionEstudianteComponent } from './informacion-del-usuario/informacion-estudiante.component';
import { EncuestasDisponiblesComponent } from './encuestas-disponibles/encuestas-disponibles.component';
import { MateriasAprobadasComponent } from './materias-aprobadas/materias-aprobadas.component';
import { CarrerasComponent } from './carreras/carreras.component';
import { MateriasComponent } from './materias/materias.component';
import { OfertaAcademicaComponent } from './oferta-academica/oferta-academica.component';
import { ComisionesDeOfertaComponent } from './comisiones-de-oferta/comisiones-de-oferta.component';
import { PeriodoComponent } from './periodos/periodo.component';
import { ComisionesComponent } from './comisiones/comisiones.component';
import { EquivalenciasComponent } from './equivalencias/equivalencias.component';
import { UsuariosComponent } from './usuarios/usuarios.component';
import { IncidenciasComponent } from './incidencias/incidencias.component';
import { EncuestasComponent } from './encuestas/encuestas.component';
import {JsonEstudiantesComponent} from './json_estudiantes/json_estudiantes.component';

const APP_ROUTES: Routes = [
  { path: '', component: LoginComponent, pathMatch: 'full' },
  { path: 'encuestas-vigentes', component: EncuestasDisponiblesComponent },
  { path: 'materias-aprobadas', component: MateriasAprobadasComponent },
  { path: 'verificacion-de-datos', component: InformacionEstudianteComponent },
  { path: 'materias-por-cursar', component: SeleccionDeMateriasPorCursarComponent },
  { path: 'encuesta-finalizada', component: EncuestaFinalizadaComponent },
  { path: 'tareas-usuario', component: TareasUsuarioComponent },
  { path: 'carreras', component: CarrerasComponent },
  { path: 'materias', component: MateriasComponent },
  { path: 'oferta-academica', component: OfertaAcademicaComponent },
  { path: 'comisiones-de-oferta', component: ComisionesDeOfertaComponent },
  { path: 'periodos', component: PeriodoComponent },
  { path: 'comisiones', component: ComisionesComponent },
  { path: 'equivalencias', component: EquivalenciasComponent },
  { path: 'usuarios', component: UsuariosComponent },
	{ path: 'incidencias', component: IncidenciasComponent },
  { path: 'encuestas', component: EncuestasComponent },
  { path: 'json-estudiantes', component: JsonEstudiantesComponent},
  { path: '**', redirectTo: '' }
];

  @NgModule({
    imports: [RouterModule.forRoot(APP_ROUTES)],
    exports: [RouterModule]
  })
  export class AppRoutingModule { }
