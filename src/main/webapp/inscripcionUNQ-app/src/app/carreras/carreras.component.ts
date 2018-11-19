import { Component, OnInit } from '@angular/core';
import { RestService } from '../rest.service';
import { UtilesService } from '../utiles.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Carrera } from './carrera.model';
import { DialogosService } from '../dialogos.service';

@Component({
	selector: 'app-carreras',
	templateUrl: './carreras.component.html',
	styleUrls: ['../estilo-abm.component.css']
})
export class CarrerasComponent implements OnInit {
	carreras: Carrera[];
	idCarrera;

	constructor(
		private restService: RestService,
		private utilesService: UtilesService,
		private dialogosService: DialogosService,
	) { }

	ngOnInit() {
		this.carreras = JSON.parse(localStorage.getItem('carreras'));
	}

	getCarreras() {
		this.restService.getCarreras().subscribe(carreras => {
			this.carreras = carreras;
		},
			(err: HttpErrorResponse) => {
				this.utilesService.mostrarMensajeDeError(err);
			});
	}

	abrirDialogoCarrera(carrera?: Carrera) {
		this.dialogosService
		.abrirDialogoCarrera(carrera)
		.subscribe(res => {
			this.getCarreras();
		});

	}

	actualizarCarrera(carrera) {
		this.restService.actualizarInformacionCarrera(carrera)
			.subscribe(res => {
				const mensaje = 'Los datos de la carrera fueron actualizados con éxito';
				this.utilesService.mostrarMensaje(mensaje);
				this.getCarreras();
			},
				(err: HttpErrorResponse) => {
					this.utilesService.mostrarMensajeDeError(err);
				});
	}

	cambiarEstado(carrera: Carrera, evento) {
		carrera.habilitada = evento.checked;
		this.actualizarCarrera(carrera);
	}
}