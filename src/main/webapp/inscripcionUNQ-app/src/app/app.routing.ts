import { Routes, RouterModule } from '@angular/router';
import { SigninScreenComponent } from './auth/signin-screen.component';
import { PollScreenComponent } from './poll/poll-screen.component';
import { DataVerificationComponent } from './data-verification/data-verification-screen.component';
import { SubjectScreenComponent} from './subject/subject-screen.component';
import { SeleccionDeMateriasPorCursarComponent } from './seleccion-de-materias-por-cursar/seleccion-de-materias-por-cursar.component';
import { EncuestaFinalizadaComponent } from './encuesta-finalizada/encuesta-finalizada.component';
import { LoginAdminScreenComponent } from './login-admin/loginadmin-screen.component';

const APP_ROUTES: Routes = [
  { path: '', component: SigninScreenComponent, pathMatch: 'full' },
  { path: 'encuestas', component: PollScreenComponent },
  { path: 'materias-aprobadas', component: SubjectScreenComponent },
  { path: 'verificacion-de-datos', component: DataVerificationComponent },
  { path: 'materias-por-cursar', component: SeleccionDeMateriasPorCursarComponent },
  { path: 'encuesta-finalizada', component: EncuestaFinalizadaComponent },
  { path: 'login-admin', component: LoginAdminScreenComponent },
  { path: '**', redirectTo: '' }
];

export const Routing = RouterModule.forRoot(APP_ROUTES);
