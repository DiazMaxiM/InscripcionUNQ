import { Component} from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { RestService } from '../rest.service';
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
    private utilesService: UtilesService
  ) {}

  dniFormControl = new FormControl('', [
      Validators.required
    ]);
  onSubmit() {
      const dni = this.dniFormControl.value;
      this.restService.getEncuestasVigentes(dni).subscribe(encuestas => {
        localStorage.setItem('dniEstudiante', dni);
        localStorage.setItem('encuestasVigentes', JSON.stringify(encuestas));
        this.utilesService.irA('encuestas');

      },
      (err: HttpErrorResponse) => {

          this.utilesService.mostrarMensajeDeError(err);
      });
  }
}
