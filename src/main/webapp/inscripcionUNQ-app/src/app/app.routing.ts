import { Routes, RouterModule } from '@angular/router';
import {SigninScreenComponent} from './auth/signin-screen.component';
import {PollScreenComponent} from './poll/poll-screen.component';
import { DataVerificationComponent } from './data-verification/data-verification-screen.component';
import { SubjectScreenComponent} from './subject/subject-screen.component';
import { SelectSubjectsComponent } from './select-subjects/selectsubjects-screen.component';

const APP_ROUTES: Routes = [
  { path: '', component: SigninScreenComponent, pathMatch: 'full' },
  { path: 'encuestas', component: PollScreenComponent },
  { path: 'materias-aprobadas', component: SubjectScreenComponent },
  { path: 'verificacion-de-datos', component: DataVerificationComponent },
  { path: '**', redirectTo: '' }
  { path: 'seleccionar-materias', component: SelectSubjectsComponent },
];

export const Routing = RouterModule.forRoot(APP_ROUTES);
