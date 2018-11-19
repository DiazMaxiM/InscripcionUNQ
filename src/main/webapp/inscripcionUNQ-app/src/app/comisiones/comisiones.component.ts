import { Component, OnInit } from '@angular/core';
import { RestService } from '../rest.service';
import { UtilesService } from '../utiles.service';
import { Periodo } from '../periodos/periodo.model';
import { Observable } from 'rxjs';
import { AppRutas } from '../app-rutas.model';
import { FormControl } from '@angular/forms';
import { startWith, map } from 'rxjs/operators';
import { AppMensajes } from '../app-mensajes.model';
import { MatOptionSelectionChange} from '@angular/material';
import { Comision } from '../comisiones-de-oferta/comision.model';
import { HttpErrorResponse } from '@angular/common/http';
import { DialogosService } from '../dialogos.service';

@Component({
	selector: 'app-comisiones',
	templateUrl: './comisiones.component.html',
	styleUrls: ['../estilo-abm.component.css']
})

export class ComisionesComponent implements OnInit {
	periodoControl = new FormControl();
	comisiones: Comision[];
	periodos: Periodo[];
	mostrarComisiones;
	comisionBuscada;
	materiaBuscada;
	periodoActual;
	periodoBuscado;

	constructor(
		private restService: RestService,
		private utilesService: UtilesService,
		private dialogosService: DialogosService
	) { }

	ngOnInit() {
		this.getPeriodos();
		this.periodoControl.valueChanges.subscribe(term => {
			this.periodoBuscado = term;
			
		});
	}

	getPeriodos() {
		this.restService.getPeriodos().subscribe(periodos => {
			this.periodos = periodos;
			this.guardarPeriodos(periodos);
		},
			(err) => {
				this.utilesService.mostrarMensajeDeError(err);
			});
	}

	guardarPeriodos(periodos: Periodo[]) {
		if (periodos.length == 0) {
			const mensaje = 'No se encontraron períodos para la oferta';
			this.utilesService.mostrarMensajeYRedireccionar(mensaje, AppRutas.TAREAS_USUARIO);
		}
		this.periodos = periodos;
	}

	periodoSeleccionado(event: MatOptionSelectionChange, periodo: Periodo) {
		if (event.source.selected) {
			this.periodoActual = periodo;
			this.getComisionesEnPeriodo(periodo);
		}
	}

	getComisionesEnPeriodo(periodo) {
		this.restService.getComisionesEnPeriodo(periodo.id).subscribe(comisiones => {
			this.guardarComisiones(comisiones);
		},
			(err) => {
				this.utilesService.mostrarMensajeDeError(err);
			});
	}

	guardarComisiones(comisiones) {
		if (comisiones.length == 0) {
			this.utilesService.mostrarMensaje(AppMensajes.NO_SE_ENCONTRARON_COMISIONES_EN_PERIODO);
			this.mostrarComisiones = false;
			this.comisiones = [];
		} else {
			this.comisiones = this.utilesService.ordenarComisionesPorNombre(comisiones);
			this.mostrarComisiones = true;
		}
	}

	abrirDialogoComision(comision?: Comision) {
		this.dialogosService
		.abrirDialogoComision(comision)
		.subscribe(res => {
			this.getComisionesEnPeriodo(this.periodoActual);
		});

	}

	mostarComisionEnPeriodo(periodo) {
		this.periodoControl = new FormControl(periodo.codigo);
		this.getComisionesEnPeriodo(periodo);
	}


	eliminarComision(comision: Comision) {
		const mensaje = '¿Está seguro de que desea eliminar la comisión seleccionada?';
		this.utilesService.mostrarDialogoConfirmacion(mensaje).subscribe(confirma => {
			if (confirma) {
				this.eliminar(comision);
			}
		});
	}

	eliminar(comision: Comision) {
		this.restService.eliminarComision(comision.id).subscribe(res => {
			this.utilesService.mostrarMensaje(AppMensajes.ELIMINACION_USUARIO_EXITOSO);
			this.getComisionesEnPeriodo(comision.periodo);
		},
			(err) => {
				this.utilesService.mostrarMensajeDeError(err);
			});
	}

	abrirDialogoParaSeleccionDePeriodo(idComision) {
		this.dialogosService
		.abrirDialogoSeleccionDePeriodo()
		.subscribe(idPeriodo => {
			if (idPeriodo != null) {
				this.clonarComision(idPeriodo, idComision);
			}
		});
	}

	clonarComision(idPeriodo, idComision) {
		const comision = new Comision();
		comision.id = idComision;
		const periodo = new Periodo();
		periodo.id = idPeriodo;
		comision.periodo = periodo;
		this.restService.clonarComisiom(comision).subscribe(
			res => {
				this.utilesService.mostrarMensaje('La comisión fue clonada con éxito');
				this.getComisionesEnPeriodo(this.periodoActual);
			},
			(err: HttpErrorResponse) => {
				this.utilesService.mostrarMensajeDeError(err);
			}
		);
	}
}