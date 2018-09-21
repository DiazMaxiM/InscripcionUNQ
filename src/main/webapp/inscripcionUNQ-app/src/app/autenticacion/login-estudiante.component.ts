import { Component} from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { RestService } from '../rest.service';
import { PollService } from '../poll/poll.service';
import { PollInfo } from '../poll/poll-info.model';
import {HttpErrorResponse } from '@angular/common/http';
import {UtilesService} from '../utiles.service';

@Component({
  selector: 'app-login-estudiante',
  templateUrl: './login-estudiante.component.html',
  styleUrls: ['./login-estudiante.component.css']
})
export class LoginEstudianteComponent {

  constructor(
    private restService: RestService,
    private pollService: PollService,
    private utilesService: UtilesService
  ) {}

  dniFormControl = new FormControl('', [
      Validators.required
    ]);
  onSubmit() {
      const dni = this.dniFormControl.value;
      this.restService.getPolls(dni).subscribe(polls => {
        const pollInfo = new PollInfo(dni,polls);
        this.pollService.sendStudentPollInfo(pollInfo);
        this.utilesService.irA('encuestas');
      },
      (err: HttpErrorResponse) => {

          this.utilesService.mostrarMensajeDeError(err);
      });
  }
}
