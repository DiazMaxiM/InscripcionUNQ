
import { Component, OnInit} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Administrador } from './administrador.model';
import { RestService } from '../rest.service';
import { HttpErrorResponse } from '@angular/common/http';
import {UtilesService} from '../utiles.service';

@Component({
  selector: 'app-loginadmin-screen',
  templateUrl: './loginadmin-screen.component.html',
  styleUrls: ['./loginadmin-screen.component.css']
})
export class LoginAdminScreenComponent implements OnInit {
    constructor(
      private formBuilder: FormBuilder,
      private restService: RestService,
      private utilesService: UtilesService
    ) {}

    loginVerificationForm: FormGroup;

    ngOnInit() {
      this.createLoginFormGroup();
    }

  createLoginFormGroup() {
      this.loginVerificationForm = this.formBuilder.group({
              password: ['', [Validators.required]],
              email: ['', [Validators.required,Validators.email]]
        });
    }

    onSubmit() {
      if (this.loginVerificationForm.valid) {
        const { email, password} = this.loginVerificationForm.value;
        const administrador = new Administrador(email,password);
        this.restService.ingresarAdministrador(administrador)
          .subscribe(res => {
             this.utilesService.irA('tareas-admin');
           },
           (err: HttpErrorResponse) => {
             this.utilesService.mostrarMensajeDeError(err);
           });
      }
    }
}
