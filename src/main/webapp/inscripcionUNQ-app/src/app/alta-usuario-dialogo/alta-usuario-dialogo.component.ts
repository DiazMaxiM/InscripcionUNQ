import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { UtilesService } from '../utiles.service';
import { RestService } from '../rest.service';
import { Usuario } from '../autenticacion/usuario.model';
import { AppMensajes } from '../app-mensajes.model';
import { DataDialogo } from './data-dialogo.model';

@Component({
	selector: 'app-alta-usuario-dialogo',
	templateUrl: './alta-usuario-dialogo.component.html',
	styleUrls: ['../dialogo-abm.component.css']
})

export class AltaUsuarioDialogoComponent implements OnInit {
	form: FormGroup;
	usuario: Usuario;

	constructor(
		private fb: FormBuilder,
		private utilesService: UtilesService,
		private dialogRef: MatDialogRef<AltaUsuarioDialogoComponent>,
		private restService: RestService,
		@Inject(MAT_DIALOG_DATA) public data: DataDialogo) {
		this.usuario = data.usuario;
	}

	ngOnInit() {
		this.crearFormularioUsuario();
		this.insertarInformacionDelUsuarioEnFormulario();
	}

	crearFormularioUsuario() {
		this.form = this.fb.group({
			nombres: ['', Validators.required],
			apellidos: ['', Validators.required],
			dni: ['', Validators.required],
			email: ['', [Validators.required, Validators.email]]
		});
	}

	insertarInformacionDelUsuarioEnFormulario() {
		if (this.usuario != null) {
			this.form.setValue({
				'nombres': this.usuario.nombre,
				'apellidos': this.usuario.apellido,
				'email': this.usuario.email,
				'dni': this.usuario.dni
			});
		}
	}

	guardar() {
		if (this.form.valid) {
			const { nombres, apellidos, email } = this.form.value;
			const usuario = new Usuario(email, nombres, apellidos);
			if (this.usuario != null) {
				this.actualizarUsuario(usuario);
			} else {
				this.crearUsuario(usuario);
			}
		} else {
			this.utilesService.validateAllFormFields(this.form);
		}
	}

	actualizarUsuario(usuario: Usuario) {
		usuario.id = this.usuario.id;
		this.restService.actualizarUsuario(usuario).subscribe((res: Response) => {
			this.utilesService.mostrarMensaje(AppMensajes.ACTUALIZACION_USUARIO_EXITOSO);
			this.cerrar();
		},
			(err) => {
				this.utilesService.mostrarMensajeDeError(err);
			});
	}

	crearUsuario(usuario: Usuario) {
		this.utilesService.activarDialogoCargando();
		this.restService.crearNuevoUsuario(usuario).subscribe((res: Response) => {
			this.utilesService.desactivarDialogoCargando();
			this.utilesService.mostrarMensaje(AppMensajes.CREACION_USUARIO_EXITOSO);
			this.cerrar();
		},
			(err) => {
				this.utilesService.desactivarDialogoCargando();
				this.utilesService.mostrarMensajeDeError(err);
			});
	}

	cerrar() {
		this.dialogRef.close();
	}

	soloNumero(evento) {
		return this.utilesService.soloNumero(evento);
	}
}