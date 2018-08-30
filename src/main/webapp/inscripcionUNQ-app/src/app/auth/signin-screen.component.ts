import { Component} from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { AuthService } from './auth.service';
import { PollService } from '../student-poll/poll.service';
import {Router} from '@angular/router';
import {StudentPollInfo} from '../student-poll/student-poll-info.model';

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
      const dni = this.dniFormControl.value;
      this.authService.login(this.dniFormControl.value).subscribe(polls => {
      const studentPollInfo = new StudentPollInfo();
      studentPollInfo.idStudent = dni;
      studentPollInfo.polls = polls;
      this.pollService.sendSetudentPollInfo(studentPollInfo);
      },
          err => {console.log('error')});
    }
      this.router.navigate(['encuestas']);
  }
}
