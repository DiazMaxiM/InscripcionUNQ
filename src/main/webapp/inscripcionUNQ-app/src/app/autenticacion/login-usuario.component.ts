
import { Component, OnInit} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Usuario } from './usuario.model';
import { RestService } from '../rest.service';
import { HttpErrorResponse } from '@angular/common/http';
import {UtilesService} from '../utiles.service';

@Component({
  selector: 'app-login-usuario',
  templateUrl: './login-usuario.component.html',
  styleUrls: ['./login-usuario.component.css']
})
export class LoginUsuarioComponent implements OnInit {
    constructor(
      private formBuilder: FormBuilder,
      private restService: RestService,
      private utilesService: UtilesService
    ) {}

    loginVerificationForm: FormGroup;

    ngOnInit() {
      this.crearLoginFormGroup();
    }

  crearLoginFormGroup() {
      this.loginVerificationForm = this.formBuilder.group({
              password: ['', [Validators.required]],
              email: ['', [Validators.required, Validators.email]]
        });
    }

    onSubmit() {
      if (this.loginVerificationForm.valid) {
        const { email, password} = this.loginVerificationForm.value;
        const usuario = new Usuario(email, password);
        this.restService.ingresarUsuario(usuario)
          .subscribe(res => {
             this.utilesService.irA('tareas-usuario');
           },
           (err: HttpErrorResponse) => {
             this.utilesService.mostrarMensajeDeError(err);
           });
      }
    }
}
