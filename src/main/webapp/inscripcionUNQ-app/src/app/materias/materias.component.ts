import { Component, OnInit } from '@angular/core';
import { RestService } from '../rest.service';
import { UtilesService } from '../utiles.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Materia } from './materia.model';
import { DialogosService } from '../dialogos.service';

@Component({
	selector: 'app-materias',
	templateUrl: './materias.component.html',
	styleUrls: ['../estilo-abm.component.css']
})
export class MateriasComponent implements OnInit {
	materias: Materia[];
	idMateria;
	materiaBuscada: string;

	constructor(
		private restService: RestService,
		private utilesService: UtilesService,
		private dialogosService: DialogosService) { }

	ngOnInit() {
		this.materias = JSON.parse(localStorage.getItem('materias'));
	}

	getMaterias() {
		this.restService.getMaterias().subscribe(materias => {
			this.materias = materias;
		},
			(err: HttpErrorResponse) => {
				this.utilesService.mostrarMensajeDeError(err);
			});
	}


	abrirDialogoMateria(materia?: Materia) {
		this.dialogosService
		.abrirDialogoMateria(materia)
		.subscribe(res => {
			this.getMaterias();
		});
		
	}

	actualizarMateria(materia) {
		this.restService.actualizarInformacionMateria(materia)
			.subscribe(res => {
				const mensaje = 'Los datos de la materia fueron actualizados con éxito';
				this.utilesService.mostrarMensaje(mensaje);
				this.getMaterias();
			},
				(err: HttpErrorResponse) => {
					this.utilesService.mostrarMensajeDeError(err);
				});
	}


	cambiarEstado(materia: Materia, evento) {
		materia.estado = evento.checked;
		this.actualizarMateria(materia);
	}

	abrirDialogoPrerrequisito(materia?: Materia) {
		this.dialogosService
		.abrirDialogoPrerrequisito(materia)
		.subscribe(res => {
			this.getMaterias();
		});
		
	}
}