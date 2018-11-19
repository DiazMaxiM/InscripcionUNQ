import { Component, OnInit } from '@angular/core';
import { RestService } from '../rest.service';
import { UtilesService } from '../utiles.service';
import { Comision } from './comision.model';
import { HttpErrorResponse } from '@angular/common/http';
import { OfertaAcademica } from '../oferta-academica/oferta-academica.model';
import { DialogosService } from '../dialogos.service';

@Component({
	selector: 'app-comisiones-de-oferta',
	templateUrl: './comisiones-de-oferta.component.html',
	styleUrls: ['../estilo-abm.component.css']
})

export class ComisionesDeOfertaComponent implements OnInit {
	comisiones: Comision[];
	idOferta;
	oferta: OfertaAcademica;
	comisionSeleccionada;
	comisionBuscada;

	constructor(
		private restService: RestService,
		private utilesService: UtilesService,
		private dialogosService: DialogosService
	) { }

	ngOnInit() {

		this.oferta = JSON.parse(localStorage.getItem('oferta-seleccionada'));
		this.getComisiones(this.oferta.id);
	}

	getComisiones(id) {
		this.restService.getComisionesDeOferta(id).subscribe(comisiones => {
			this.comisiones = this.utilesService.ordenarComisionesPorNombre(comisiones);
		},
			(err: HttpErrorResponse) => {
				this.utilesService.mostrarMensajeDeError(err);
			});
	}

	quitarComisionDeOferta(idComision) {
		const mensaje = '¿Está seguro de que desea quitar la comisión seleccionada de la oferta académica?';
		this.utilesService.mostrarDialogoConfirmacion(mensaje).subscribe(confirma => {
			if (confirma) {
				this.quitarComision(idComision);
			}
		});
	}

	quitarComision(idComision) {
		this.restService.quitarComisionDeOferta(idComision, this.oferta.id).subscribe(res => {
			this.utilesService.mostrarMensaje('La comisión fue removida con éxito');
			this.getComisiones(this.oferta.id);
		},
			(err: HttpErrorResponse) => {
				this.utilesService.mostrarMensajeDeError(err);
			});
	}

	abrirDialogoComision() {
		this.dialogosService
		.abrirDialogoComisionesDeOferta()
		.subscribe(val => {
			if (val != undefined) {
				this.actualizarComisiones(val);
			}
		});
	}

	actualizarComisiones(comisiones) {
		this.restService.actualizarComisionesDeOferta(this.oferta.id, comisiones).subscribe(data => {
			const mensaje = 'Los datos de la oferta académica fueron actualizados con éxito';
			this.utilesService.mostrarMensaje(mensaje);
			this.getComisiones(this.oferta.id);
		},
			(err) => {
				this.utilesService.mostrarMensajeDeError(err);
			});
	}
}