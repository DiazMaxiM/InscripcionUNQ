import { Component} from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { RestService } from '../rest.service';
import { PollService } from '../poll/poll.service';
import { Router } from '@angular/router';
import { PollInfo } from '../poll/poll-info.model';

@Component({
  selector: 'app-signin-screen',
  templateUrl: './signin-screen.component.html',
  styleUrls: ['./signin-screen.component.css']
})
export class SigninScreenComponent {

  constructor(
    private restService: RestService,
    private router: Router,
    private pollService: PollService
  ) {}

  dniFormControl = new FormControl('', [
      Validators.required
    ]);
  onSubmit() {
    if (this.dniFormControl.valid) {
      const dni = this.dniFormControl.value;
      this.restService.login(this.dniFormControl.value).subscribe(polls => {
      const pollInfo = new PollInfo(dni,polls);
      this.pollService.sendStudentPollInfo(pollInfo);
      },
          err => {console.log('error')});
    }
      this.router.navigate(['encuestas']);
  }
}
