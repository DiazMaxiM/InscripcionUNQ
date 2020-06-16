import { Component, OnInit, OnDestroy } from '@angular/core';
import { RestService } from './rest.service';
import { UtilesService } from './utiles.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Incidencia } from './incidencia-dialogo/incidencia.model';
import { UsuarioLogueadoService } from './usuario-logueado.service';
import { DialogosService } from './dialogos.service';
import { Subscription } from 'rxjs';

@Component({
	selector: 'app-root',
	templateUrl: './app.component.html',
	styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit, OnDestroy {
	subUsuarioLogueado: Subscription = null;
	hayUsuarioLogueado: boolean = false;
	perfilUsuarioLogueado: string;

	constructor(
		private restService: RestService,
		private utilesService: UtilesService,
		private usuarioService: UsuarioLogueadoService,
		private dialogosService: DialogosService
	) { }

	ngOnInit() {
		console.log("localstorage", localStorage);
		if (localStorage.getItem('usuario') != null) {
			let perfil = JSON.parse(localStorage.getItem('usuario')).perfiles[0];

			this.perfilUsuarioLogueado = perfil;
			this.hayUsuarioLogueado = true;
		} else {
			this.subUsuarioLogueado = this.usuarioService.getUsuarioLogueado().subscribe(res => {
				this.hayUsuarioLogueado = res;
			});
			this.usuarioService.getPerfilUsuarioLogueado().subscribe(res => {
				this.perfilUsuarioLogueado = res;
			});
		}
	}

	ngOnDestroy() {
		if(this.subUsuarioLogueado) { this.subUsuarioLogueado.unsubscribe() };
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
		localStorage.clear();
	}
}