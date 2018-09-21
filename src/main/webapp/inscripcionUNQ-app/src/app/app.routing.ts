import { Routes, RouterModule } from '@angular/router';
import { PollScreenComponent } from './poll/poll-screen.component';
import { DataVerificationComponent } from './data-verification/data-verification-screen.component';
import { SubjectScreenComponent} from './subject/subject-screen.component';
import { SeleccionDeMateriasPorCursarComponent } from './seleccion-de-materias-por-cursar/seleccion-de-materias-por-cursar.component';
import { EncuestaFinalizadaComponent } from './encuesta-finalizada/encuesta-finalizada.component';
import { LoginUsuarioComponent } from './autenticacion/login-usuario.component';
import { TareasAdminScreenComponent } from './tareas-admin/tareasadmin-screen.component';
import { LoginEstudianteComponent } from './autenticacion/login-estudiante.component';

const APP_ROUTES: Routes = [
  { path: '', component: LoginEstudianteComponent, pathMatch: 'full' },
  { path: 'encuestas', component: PollScreenComponent },
  { path: 'materias-aprobadas', component: SubjectScreenComponent },
  { path: 'verificacion-de-datos', component: DataVerificationComponent },
  { path: 'materias-por-cursar', component: SeleccionDeMateriasPorCursarComponent },
  { path: 'encuesta-finalizada', component: EncuestaFinalizadaComponent },
  { path: 'login-usuario', component: LoginUsuarioComponent },
  { path: 'tareas-usuario', component: TareasAdminScreenComponent },
  { path: '**', redirectTo: '' }
];

export const Routing = RouterModule.forRoot(APP_ROUTES);
