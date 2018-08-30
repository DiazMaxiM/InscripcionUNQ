import { Component} from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { AuthService } from './auth.service';
import { PollService } from '../student-poll/poll.service';
import {Router} from '@angular/router';
import {Poll} from '../student-poll/poll.model';

@Component({
  selector: 'app-signin-screen',
  templateUrl: './signin-screen.component.html'
})
export class SigninScreenComponent {

  constructor(
    private authService: AuthService,
    private router: Router,
    private pollService: PollService
  ) {}

  dniFormControl = new FormControl('', [
      Validators.required
    ]);
  onSubmit() {
    if (this.dniFormControl.valid) {
      this.authService.login(this.dniFormControl.value).subscribe(polls => {
      console.log(polls);
      this.pollService.sendPolls(polls);
      },
          err => {console.log('error')});
    }
      this.router.navigate(['encuestas']);
  }
}
