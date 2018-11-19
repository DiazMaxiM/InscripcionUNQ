import { Component, OnInit } from '@angular/core';
import { RestService } from '../rest.service';
import { UtilesService } from '../utiles.service';
import { Usuario } from '../autenticacion/usuario.model';
import { AppMensajes } from '../app-mensajes.model';
import { DialogosService } from '../dialogos.service';

@Component({
	selector: 'app-periodo',
	templateUrl: './usuarios.component.html',
	styleUrls: ['../estilo-abm.component.css']
})
export class UsuariosComponent implements OnInit {

	perfiles: String[];
	usuarios: Usuario[];
	usuarioBuscado: string;
	mostrarUsuarios;
	perfilActual;

	constructor(
		private restService: RestService,
		private utilesService: UtilesService,
		private dialogosService: DialogosService
	) { }

	ngOnInit() {
		this.perfilSeleccionado('ADMINISTRADOR');
		this.getPerfiles();
	}

	getPerfiles() {
		this.restService.getTipoPerfiles().subscribe(perfiles => {
			this.perfiles = perfiles;
		},
			(err) => {
				this.utilesService.mostrarMensajeDeError(err);
			});
	}


	abrirDialogoParaAltaModificacionDeUsuario(usuario?: Usuario) {
		this.dialogosService.abrirDialogoUsuario(usuario).subscribe(res => {
			this.getUsuariosSegunPerfil();
		});
	}

	getUsuariosSegunPerfil() {
		this.restService.getUsuarios(this.perfilActual).subscribe(usuarios => {
			this.usuarios = usuarios;
			this.mostrarUsuarios = this.usuarios.length > 0;
		},
			(err) => {
				this.utilesService.mostrarMensajeDeError(err);
			});
	}


	eliminarUsuario(usuario) {
		const mensaje = '¿Está seguro de que desea eliminar el usuario seleccionada?';
		this.utilesService.mostrarDialogoConfirmacion(mensaje).subscribe(confirma => {
			if (confirma) {
				this.eliminar(usuario);
			}
		});
	}

	eliminar(usuario) {
		this.restService.eliminarUsuario(usuario.id).subscribe(res => {
			this.utilesService.mostrarMensaje(AppMensajes.ELIMINACION_USUARIO_EXITOSO);
			this.getUsuariosSegunPerfil();
		},
			(err) => {
				this.utilesService.mostrarMensajeDeError(err);
			});
	}

	perfilSeleccionado(perfil) {
		this.perfilActual = perfil;
		this.getUsuariosSegunPerfil();
	}

	abrirDialogoParaActualizacionPerfil(usuario) {
		this.dialogosService.abrirDialogoPerfil(usuario).subscribe(res => {
			this.getUsuariosSegunPerfil();
		});
	}
}