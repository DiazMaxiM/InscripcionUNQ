import { Routes, RouterModule } from '@angular/router';
import {SigninScreenComponent} from './auth/signin-screen.component';
import {PollScreenComponent} from './student-poll/poll-screen.component';


const APP_ROUTES: Routes = [
  { path: '', component: SigninScreenComponent, pathMatch: 'full' },
  { path: 'encuestas', component: PollScreenComponent },
];

export const Routing = RouterModule.forRoot(APP_ROUTES);
