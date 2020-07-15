import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { Materia } from '../materias/materia.model';
import { RestService } from '../rest.service';
import { UtilesService } from '../utiles.service';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
	selector: 'app-prerrequisitos-materia-dialogo',
	templateUrl: './prerrequisitos-materia-dialogo.component.html',
	styleUrls: ['./prerrequisitos-materia-dialogo.component.css']
})
export class PrerrequisitosMateriaDialogoComponent implements OnInit {
	materiaSeleccionada: Materia;
	materiaActual: String;
	materias: Materia[];
	prerrequisitos: Materia[];

	constructor(
		private utilesService: UtilesService,
		private restService: RestService,
		private dialogRef: MatDialogRef<PrerrequisitosMateriaDialogoComponent>) {
	}

	ngOnInit() {
		this.materiaActual = '';		
		this.prerrequisitos = this.materiaSeleccionada.prerrequisitos;
		this.getMaterias();
	}

	getMaterias() {
		this.restService.getMaterias().subscribe(materias => {
			this.materias = materias;
			this.materias = this.eliminarMateria(this.materiaSeleccionada.nombre);
		},
			(err) => {
				this.utilesService.mostrarMensajeDeError(err);
			});
	}

	agregarPrerrequisito(materia: Materia) {
		this.materiaActual = materia.nombre;
		this.prerrequisitos.push(materia);
	}

	obtenerIdsPrerrequisitos(): any {
		let materias = [];
		for (let materia of this.prerrequisitos) {
		  let materiaSeleccionada = new Materia();
		  materiaSeleccionada.id = materia.id;
		  materias.push(materiaSeleccionada);
		}

		return materias;
	}

	guardar() {
		let idsPrerrequisitos = this.obtenerIdsPrerrequisitos();
		let idMateria = this.materiaSeleccionada.id.toString();

		this.restService.actualizarPrerrequisitosMateria(idMateria, idsPrerrequisitos)
			.subscribe(res => {
				const mensaje = 'El prerrequisito se agregó correctamente';
				this.utilesService.mostrarMensaje(mensaje);
				this.cerrar();
			},
				(err: HttpErrorResponse) => {
					this.utilesService.mostrarMensajeDeError(err);
				});
	}

	cerrar() {
		this.dialogRef.close();
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

	eliminarMateria(nombreMateria){
		console.log(nombreMateria);
		return this.materias.filter(materia => materia.nombre != nombreMateria);
	}
}