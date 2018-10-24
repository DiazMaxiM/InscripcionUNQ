import {Component, OnInit} from '@angular/core';
import { MatDialogRef} from '@angular/material';
import {FormBuilder, Validators, FormGroup} from '@angular/forms';
import { UtilesService } from '../utiles.service';
import { RestService } from '../rest.service';
import { Usuario } from '../autenticacion/usuario.model';
import { AppMensajes } from '../app-mensajes.model';

@Component({
    selector: 'app-alta-usuario-dialogo',
    templateUrl: './alta-usuario-dialogo.component.html',
    styleUrls: ['../dialogo-abm.component.css']
})

export class AltaUsuarioDialogoComponent implements OnInit {

    form: FormGroup;


    constructor(
        private fb: FormBuilder,
        private utilesService: UtilesService,
        private dialogRef: MatDialogRef<AltaUsuarioDialogoComponent>,
        private restService: RestService ) {
    }
    ngOnInit() {
        this.crearFormularioUsuario();

    }

    crearFormularioUsuario() {
        this.form = this.fb.group({
            email: ['', [Validators.required, Validators.email]]
          });
    }

    guardar() {
        if (this.form.valid) {
             const { email} = this.form.value;
            const usuario = new Usuario(email);
            this.crearUsuario(usuario);
        } else {
            this.utilesService.validateAllFormFields(this.form);
        }
    }


    crearUsuario(usuario: Usuario) {
       this.restService.crearNuevoUsuario(usuario).subscribe((res: Response) => {
        this.dialogRef.close(AppMensajes.OK);
      },
      (err) => {
          this.utilesService.mostrarMensajeDeError(err);
      });
    }

    cerrar() {
        this.dialogRef.close();
    }

}
