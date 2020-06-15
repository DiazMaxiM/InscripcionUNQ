import { Component, OnInit } from '@angular/core';
import { Validators, FormBuilder, FormGroup } from '@angular/forms';
import { RestService } from '../rest.service';
import { HttpErrorResponse } from '@angular/common/http';
import { UtilesService } from '../utiles.service';
import { Usuario } from './usuario.model';
import { AppMensajes } from '../app-mensajes.model';
import { AppRutas } from '../app-rutas.model';
import { UsuarioLogueadoService } from '../usuario-logueado.service';

@Component({
	selector: 'app-login',
	templateUrl: './login.component.html',
	styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
	perfiles: String[];
	seleccionaPerfil: boolean;
	perfilSeleccionado: string;
	loginVerificationForm: FormGroup;

	constructor(
		private formBuilder: FormBuilder,
		private restService: RestService,
		private utilesService: UtilesService,
		private usuarioLogueado: UsuarioLogueadoService
	) { }

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
			const { email, password } = this.loginVerificationForm.value;
			const usuario = new Usuario(email);
			usuario.password = password;
			this.restService.ingresarUsuario(usuario)
				.subscribe(infoUsuario => {
					this.mostrarPantallaSegunPerfil(infoUsuario);
				},
					(err: HttpErrorResponse) => {
						this.utilesService.mostrarMensajeDeError(err);
					});
		}
	}

	recuperarPassword() {
		const { email, password } = this.loginVerificationForm.value;
		if (email != "") {
			const usuario = new Usuario(email);
			this.utilesService.activarDialogoCargando('Enviando nueva contraseña...');
			this.restService.recuperarPassword(usuario)
				.subscribe(infoUsuario => {
					this.utilesService.desactivarDialogoCargando();
					this.utilesService.mostrarMensajeYSalir("Se envió la nueva contraseña al e-mail indicado");
				},
					(err: HttpErrorResponse) => {
						this.utilesService.mostrarMensajeDeError(err);
						this.utilesService.desactivarDialogoCargando();
					});
		}
	}

	mostrarPantallaSegunPerfil(usuario: Usuario) {
		this.usuarioLogueado.notificarUsuarioLoguedado();
		localStorage.setItem('usuario', JSON.stringify(usuario));
		if (usuario.perfiles.length > 1) {
			this.seleccionaPerfil = true;
			this.perfiles = usuario.perfiles;
		} else {
			const perfil = usuario.perfiles[0];
			this.irASegunPerfil(perfil);
		}
	}

	irASegunPerfil(perfil: string) {
		this.usuarioLogueado.notificarPerfilUsuarioLogueado(perfil);

		if (AppMensajes.ESTUDIANTE == perfil) {
			this.utilesService.irA(AppRutas.ENCUESTAS_VIGENTES);
		} else if (AppMensajes.ADMINSTRADOR == perfil) {
			this.utilesService.irA(AppRutas.TAREAS_USUARIO);
		}
	}

	irAPerfilSeleccionado(perfil) {
		this.perfilSeleccionado = perfil;
		this.irASegunPerfil(perfil);
	}
}