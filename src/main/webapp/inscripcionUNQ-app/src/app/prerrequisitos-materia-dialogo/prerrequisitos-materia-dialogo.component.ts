import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { Materia } from '../materias/materia.model';
import { RestService } from '../rest.service';
import { UtilesService } from '../utiles.service';

@Component({
	selector: 'app-prerrequisitos-materia-dialogo',
	templateUrl: './prerrequisitos-materia-dialogo.component.html',
	styleUrls: ['./prerrequisitos-materia-dialogo.component.css']
})
export class PrerrequisitosMateriaDialogoComponent implements OnInit {
	materiaSeleccionada: Materia;
	materiaActual: String;
	materias: Materia[];
	tienePrerrequisitos: boolean = false;
	prerrequisitos: Materia[];

	constructor(
		private utilesService: UtilesService,
		private restService: RestService,
		private dialogRef: MatDialogRef<PrerrequisitosMateriaDialogoComponent>) {
	}

	ngOnInit() {
		this.materiaActual = '';
		this.prerrequisitos = [];
		//this.prerrequisitos = this.getPrerrequisitos();
		this.getMaterias();
	}

	getMaterias() {
		this.restService.getMaterias().subscribe(materias => {
			this.materias = materias;
		},
			(err) => {
				this.utilesService.mostrarMensajeDeError(err);
			});
	}

	agregarPrerrequisito(materia: Materia) {
		this.materiaActual = materia.nombre;
		this.prerrequisitos.push(materia);
	}

	eliminarPrerrequisito(prerrequisito: Materia) {
		let index = this.prerrequisitos.findIndex(p => p === prerrequisito)
		this.prerrequisitos.splice(index, 1);
	}

	rollback() {
		//TODO
	}

	actualizarMateriaActual() {
		this.materiaActual = '';
	}

	guardar() {
		/*
				this.restService.actualizarPrerrequisitosMateria(this.prerrequisitos)
					.subscribe(res => {
						const mensaje = '';
						this.utilesService.mostrarMensaje(mensaje);
						this.cerrar();
					},
						(err: HttpErrorResponse) => {
							this.utilesService.mostrarMensajeDeError(err);
						});
			
					*/
	}

	cerrar() {
		this.dialogRef.close();
	}
}