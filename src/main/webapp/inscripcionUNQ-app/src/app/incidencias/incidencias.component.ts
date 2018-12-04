import { Component, OnInit } from '@angular/core';
import { RestService } from '../rest.service';
import { UtilesService } from '../utiles.service';
import { MatDialog, MatOptionSelectionChange } from '@angular/material';
import { HttpErrorResponse } from '@angular/common/http';
import { IncidenciaEstado } from './incidencia-estado.model';
import { TipoIncidencia } from '../tipo-incidencia-dialogo/tipo-incidencia.model';
import { DialogosService } from '../dialogos.service';
import { FormControl } from '@angular/forms';

@Component({
	selector: 'app-incidencias',
	templateUrl: './incidencias.component.html',
	styleUrls: ['../estilo-abm.component.css']
})
export class IncidenciasComponent implements OnInit {
	incidencias: IncidenciaEstado[];
	idIncidencia;
	tiposDeIncidencias: TipoIncidencia[];
	tipoIncidenciaBuscada;
	tipoIncidenciaControl = new FormControl();
	hayIncidenciasReportadas;
	tipoDeIncidenciaActual: TipoIncidencia;

	constructor(
		private restService: RestService,
		private utilesService: UtilesService,
		private dialogosService: DialogosService,
		private dialog: MatDialog
	) { }

	ngOnInit() {
		this.getTiposDeIncidencias();
		this.tipoIncidenciaControl.valueChanges.subscribe((term) => {
			this.tipoIncidenciaBuscada = term;
			
		});
	}

	getTiposDeIncidencias() {
		this.restService.getTiposIncidencias().subscribe(tipoIncidencias => {
			this.tiposDeIncidencias = tipoIncidencias;
		},
			(err) => {
				this.utilesService.mostrarMensajeDeError(err);
			});
	}

	getIncidenciasDelTipo(tipoIncidencia: TipoIncidencia) {
		this.restService.getIncidencias(tipoIncidencia.id).subscribe(incidencias => {
			this.guardarIncidencias(incidencias);

		},
			(err) => {
				this.utilesService.mostrarMensajeDeError(err);
			});
	}

	guardarIncidencias(incidencias: TipoIncidencia[]){
		if(incidencias.length == 0){
			this.hayIncidenciasReportadas = false;
			this.utilesService.mostrarMensaje('No se registraron incidencias de tipo: ' + this.tipoDeIncidenciaActual.descripcion);
		} else{
			this.incidencias = incidencias;
			this.hayIncidenciasReportadas = true;
		}
	}

	actualizarIncidenciaSeleccionada(incidencia: IncidenciaEstado, idIncidencia) {
		incidencia.id = idIncidencia;
		this.actualizarIncidencia(incidencia);
	}

	actualizarIncidencia(incidencia) {
		this.restService.actualizarIncidencia(incidencia)
			.subscribe(res => {
				const mensaje = 'Los datos de la incidencia fueron actualizados con éxito';
				this.utilesService.mostrarMensaje(mensaje);
				this.getIncidenciasDelTipo(this.tipoDeIncidenciaActual);
			},
				(err: HttpErrorResponse) => {
					this.utilesService.mostrarMensajeDeError(err);
				});
	}

	cambiarEstado(tipoEstadoIncidencia, incidencia: IncidenciaEstado) {
		incidencia.tipoEstadoIncidencia = tipoEstadoIncidencia;
		this.actualizarIncidencia(incidencia);
	}

	abrirDialogoParaEdicionDeIncidencia(incidencia: IncidenciaEstado) {
		this.dialogosService.abrirDialogoEdicionDeIncidencia(incidencia).subscribe(val => {
			if (val != undefined) {
				this.cambiarEstado(val, incidencia);
				this.getIncidenciasDelTipo(this.tipoDeIncidenciaActual);
			}
		});
	}

	abrirDialogoTipoIncidencia(tipoIncidencia?) {
		this.dialogosService
		.abrirDialogoTipoDeIncidencia(tipoIncidencia)
		.subscribe(res => {
			this.getTiposDeIncidencias();
		});
}

incidenciaSeleccionada(event: MatOptionSelectionChange, tipoIncidencia){
	if (event.source.selected) {
		this.tipoDeIncidenciaActual = tipoIncidencia;
		this.getIncidenciasDelTipo(tipoIncidencia);
	}
}

}
