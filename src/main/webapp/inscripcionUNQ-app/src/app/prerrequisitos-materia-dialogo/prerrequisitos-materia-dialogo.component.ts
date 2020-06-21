import { HttpErrorResponse } from '@angular/common/http';
import { Component,OnInit } from '@angular/core';
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
  materia: Materia;
  materias: Materia[];
  tienePrerrequisitos: boolean = false;
  prerrequisitos: Materia[];

	constructor(
		private utilesService: UtilesService,
		private restService: RestService,
		private dialogRef: MatDialogRef<PrerrequisitosMateriaDialogoComponent>) {
	}

	ngOnInit() {
    //this.prerrequisitos = this.getPrerrequisitos();
    //this.tienePrerrequisitos = this.prerrequisitos.lenght > 0;
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
		this.restService.actualizarPrerrequisitosMateria(materia)
			.subscribe(res => {
				const mensaje = '';
				this.utilesService.mostrarMensaje(mensaje);
				this.cerrar();
			},
				(err: HttpErrorResponse) => {
					this.utilesService.mostrarMensajeDeError(err);
				});
	}

	actualizarMateriaSeleccionada(materia: Materia, idMateria) {
		materia.id = idMateria;
		this.actualizarMateria(materia);
	}

	actualizarMateria(materia) {
		this.restService.actualizarInformacionMateria(materia)
			.subscribe(res => {
				const mensaje = 'Los datos de la materia fueron actualizados con éxito';
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
}