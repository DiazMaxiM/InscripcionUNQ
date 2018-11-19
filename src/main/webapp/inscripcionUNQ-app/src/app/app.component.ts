import { Component, OnInit, AfterViewInit } from '@angular/core';
import { RestService } from './rest.service';
import { UtilesService } from './utiles.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Incidencia } from './incidencia-dialogo/incidencia.model';
import { UsuarioLogueadoService } from './usuario-logueado.service';
import { DialogosService } from './dialogos.service';
import { Usuario } from './autenticacion/usuario.model';
import { AppMensajes } from './app-mensajes.model';

@Component({
	selector: 'app-root',
	templateUrl: './app.component.html',
	styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit, AfterViewInit {
	title = 'Inscripción UNQ';
	incidencia: Incidencia;
	hayUsuarioLogueado: boolean;
	muestraDialogoDeIncidencia = true;
	usuario: Usuario;

	constructor(
		private restService: RestService,
		private utilesService: UtilesService,
		private usuarioLogueado: UsuarioLogueadoService,
		private dialogosService: DialogosService
	) { }

	ngOnInit() {
		this.usuarioLogueado.hayUsuarioLogueaado.subscribe(res => {
			this.usuario = JSON.parse(localStorage.getItem('usuario'));
			this.hayUsuarioLogueado = res;
			this.hayEstudianteLogueado();

		});
	}

	hayEstudianteLogueado(){
		if (this.usuario != null){
			this.muestraDialogoDeIncidencia = this.usuario.perfiles.includes(AppMensajes.ESTUDIANTE);
		}
	}

	ngAfterViewInit() {
		this.usuarioLogueado.hayUsuarioLogueaado.subscribe(res => {
			this.hayUsuarioLogueado = res;
			this.hayEstudianteLogueado();
		});
	}

	abrirDialogoParaLaCreacionDeIncidencia() {
		this.dialogosService.abrirDialogoReporteIncidencia().subscribe(val => {
			if (val != undefined) {
				this.crearNuevaIncidencia(val);
			}
		});
	}

	crearNuevaIncidencia(incidencia: Incidencia) {
		this.restService.agregarIncidencia(incidencia)
			.subscribe(res => {
				const mensaje = 'Se envió con éxito la incidencia';
				this.utilesService.mostrarMensaje(mensaje);
			},
				(err: HttpErrorResponse) => {
					this.utilesService.mostrarMensajeDeError(err);
				});
	}

	abrirDialogoParaModificacionDePassword() {
    this.dialogosService.abrirDialogoModificacionPassword();
	}

	salir() {
		this.utilesService.salir();
	}

}