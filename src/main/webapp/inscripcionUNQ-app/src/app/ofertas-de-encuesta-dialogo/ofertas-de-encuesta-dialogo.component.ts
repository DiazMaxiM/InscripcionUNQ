import { Component, OnInit } from '@angular/core';
import { UtilesService } from '../utiles.service';
import { RestService } from '../rest.service';
import { AppMensajes } from '../app-mensajes.model';
import { OfertaAcademica } from '../oferta-academica/oferta-academica.model';
import { Encuesta } from '../encuesta-dialogo/encuesta.model';
import { MatDialogRef } from '@angular/material';

@Component({
	selector: 'app-ofertas-de-encuesta-dialogo',
	templateUrl: './ofertas-de-encuesta-dialogo.component.html',
	styleUrls: ['../dialogo-abm.component.css']
})

export class OfertasDeEncuestaDialogoComponent implements OnInit {
	ofertas: OfertaAcademica[];
	ofertasSeleccionados: OfertaAcademica[] = [];
	encuesta: Encuesta;
	ofertaBuscada;

	constructor(
		private restService: RestService,
		private utilesService: UtilesService,
		private dialogRef: MatDialogRef<OfertasDeEncuestaDialogoComponent>) {
	}

	ngOnInit() {
		this.getOfertasEnPeriodo();
	}

	getOfertasEnPeriodo() {
		this.restService.getOfertasEnPeriodo(this.encuesta.periodo.id).subscribe(ofertas => {
			this.guardarOfertas(ofertas);
		},
			(err) => {
				this.utilesService.mostrarMensajeDeError(err);
			});
	}

	guardarOfertas(ofertas: OfertaAcademica[]) {
		if (ofertas.length == 0) {
			this.utilesService.mostrarMensaje(AppMensajes.N0_HAY_OFERTAS_EN_PERIODO + this.encuesta.periodo.codigo);
			this.cerrar();
		} else {
			this.ofertas = ofertas;
		}
	}

	onChange(oferta, $event) {
		if ($event.checked) {
			this.ofertasSeleccionados.push(oferta);
		} else {
			this.ofertasSeleccionados.forEach(ofertaSeleccionada => {
				if (ofertaSeleccionada.id == oferta.id) {
					this.ofertasSeleccionados.splice(this.ofertasSeleccionados.indexOf(ofertaSeleccionada), 1);
				}
			});
		}
	}

	verificarSeleccion(oferta) {
		let ofertaChecked = false;
		this.ofertasSeleccionados.forEach(ofertaSeleccionada => {
			if (oferta.id == ofertaSeleccionada.id) {
				ofertaChecked = true;
			}
		});
		return ofertaChecked;
	}


	guardar() {
		if (this.ofertasSeleccionados.length == 0) {
			this.utilesService.mostrarMensaje('Debe seleccionar al menos una oferta académica');
		} else {
			this.actualizarOfertasParaEncuesta();
		}
	}

	actualizarOfertasParaEncuesta() {
		this.restService.actualizarOfertasDeEncuesta(this.encuesta.id, this.ofertasSeleccionados).subscribe(res => {
			this.utilesService.mostrarMensaje(AppMensajes.ACTUALIZACION_OFERTAS_DE_ENCUESTA_EXITOS0);
			this.cerrar();
		},
			(err) => {
				this.utilesService.mostrarMensajeDeError(err);
			});
	}

	cerrar() {
		this.dialogRef.close();
	}
}