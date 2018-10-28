import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { UtilesService } from '../utiles.service';
import { RestService } from '../rest.service';
import { Usuario } from '../autenticacion/usuario.model';
import { AppMensajes } from '../app-mensajes.model';

@Component({
	selector: 'app-edicion-usuario-dialogo',
	templateUrl: './edicion-usuario-dialogo.component.html',
	styleUrls: ['../dialogo-abm.component.css']
})

export class EdicionUsuarioDialogoComponent implements OnInit {
	form: FormGroup;
	tipoPeriodos: string[] = [];
	idUsuario;

	constructor(
		private fb: FormBuilder,
		private utilesService: UtilesService,
		private dialogRef: MatDialogRef<EdicionUsuarioDialogoComponent>,
		private restService: RestService) {
	}

	ngOnInit() {
		this.crearFormularioUsuario();
		this.idUsuario = localStorage.getItem('idUsuario');
	}

	crearFormularioUsuario() {
		this.form = this.fb.group({
			password: [null, [Validators.required]],
			repassword: [null, [Validators.required]]
		}, { validator: this.checkIfMatchingPasswords('password', 'repassword') });
	}

	checkIfMatchingPasswords(passwordKey: string, passwordConfirmationKey: string) {
		return (group: FormGroup) => {
			const passwordInput = group.controls[passwordKey],
				passwordConfirmationInput = group.controls[passwordConfirmationKey];
			if (passwordInput.value !== passwordConfirmationInput.value) {
				return passwordConfirmationInput.setErrors({ notEquivalent: true })
			}
		}
	}

	guardar() {
		if (this.form.valid) {
			const { password } = this.form.value;
			const usuario = new Usuario();
			usuario.id = this.idUsuario;
			usuario.password = password;
			this.actualizarPassword(usuario);
		} else {
			this.utilesService.validateAllFormFields(this.form);
		}
	}

	actualizarPassword(usuario: Usuario) {
		this.restService.actualizarPassword(usuario).subscribe((res: Response) => {
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