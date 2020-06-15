import { Component, OnInit } from '@angular/core';
import { RestService } from './rest.service';
import { UtilesService } from './utiles.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Incidencia } from './incidencia-dialogo/incidencia.model';
import { UsuarioLogueadoService } from './usuario-logueado.service';
import { DialogosService } from './dialogos.service';

@Component({
	selector: 'app-root',
	templateUrl: './app.component.html',
	styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
	hayUsuarioLogueado: boolean = false;
	perfilUsuarioLogueado: string;

	constructor(
		private restService: RestService,
		private utilesService: UtilesService,
		private usuarioLogueado: UsuarioLogueadoService,
		private dialogosService: DialogosService
	) { }

	ngOnInit() {
		this.usuarioLogueado.getUsuarioLogueado().subscribe(res => {
			this.hayUsuarioLogueado = res;
		});
		this.usuarioLogueado.getPerfilUsuarioLogueado().subscribe(res => {
			this.perfilUsuarioLogueado = res;
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