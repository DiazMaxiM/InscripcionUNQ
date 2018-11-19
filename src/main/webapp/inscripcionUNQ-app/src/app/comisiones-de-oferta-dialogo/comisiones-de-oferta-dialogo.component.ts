import { Component, OnInit } from '@angular/core';
import { Comision } from '../comisiones-de-oferta/comision.model';
import { UtilesService } from '../utiles.service';
import { RestService } from '../rest.service';
import { HttpErrorResponse } from '@angular/common/http';
import { OfertaAcademica } from '../oferta-academica/oferta-academica.model';
import { MatDialogRef } from '@angular/material';
import { AppRutas } from '../app-rutas.model';

@Component({
	selector: 'app-comisiones-de-oferta-dialogo',
	templateUrl: './comisiones-de-oferta-dialogo.component.html',
	styleUrls: ['../dialogo-abm.component.css']
})

export class ComisionesDeOfertaDialogoComponent implements OnInit {
	comisiones: Comision[];
	comisionesSeleccionadas: Comision[] = [];
	idOferta;
	oferta: OfertaAcademica;
	comisionSeleccionada;
	comisionChecked: boolean;
	comisionBuscada;

	constructor(
		private restService: RestService,
		private utilesService: UtilesService,
		private dialogRef: MatDialogRef<ComisionesDeOfertaDialogoComponent>) { }

	ngOnInit() {
		this.oferta = JSON.parse(localStorage.getItem('oferta-seleccionada'));
		this.restService.getComisionesEnPeriodo(this.oferta.periodo.id).subscribe(comisiones => {
			this.comisiones = this.utilesService.ordenarComisionesPorNombre(comisiones);
		},
			(err: HttpErrorResponse) => {
				this.utilesService.mostrarMensajeDeError(err);
			});
		this.getComisiones(this.oferta.id);
	}

	getComisiones(id) {
		this.restService.getComisionesDeOferta(id).subscribe(comisiones => {
			this.guardarComisiones(comisiones);
		},
			(err: HttpErrorResponse) => {
				this.utilesService.mostrarMensajeDeError(err);
			});
	}

	guardarComisiones(comisiones: Comision[]) {
		if (comisiones.length == 0) {
			const mensaje = 'No se encontraron comisiones para el período de la oferta';
			this.irATarerasUsuario(mensaje);
		} else {
			this.comisionesSeleccionadas = this.utilesService.ordenarComisionesPorNombre(comisiones);
		}
	}

	irATarerasUsuario(mensaje) {
		this.cerrar();
		this.utilesService.mostrarMensajeYRedireccionar(mensaje, AppRutas.TAREAS_USUARIO);
	}

	onChange(comision, $event) {
		if ($event.checked) {
			this.comisionesSeleccionadas.push(comision);
		} else {
			this.comisionesSeleccionadas.forEach(comisionSeleccionada => {
				if (comision.id == comisionSeleccionada.id) {
					this.comisionesSeleccionadas.splice(this.comisionesSeleccionadas.indexOf(comisionSeleccionada), 1);
				}
			});
		}
	}

	verificarSeleccion(comision) {
		this.comisionChecked = false;
		this.comisionesSeleccionadas.forEach(carreraSeleccionada => {
			if (comision.id == carreraSeleccionada.id) {
				this.comisionChecked = true;
			}
		});
		return this.comisionChecked;
	}

	guardar() {
		if (this.comisionesSeleccionadas.length == 0) {
			this.utilesService.mostrarMensaje('No ha seleccionado ninguna comisión')
		} else {
			this.dialogRef.close(this.comisionesSeleccionadas);
		}
	}

	cerrar() {
		this.dialogRef.close();
	}
}