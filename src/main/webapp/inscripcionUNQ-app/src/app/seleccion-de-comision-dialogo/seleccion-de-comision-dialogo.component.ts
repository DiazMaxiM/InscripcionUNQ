import { Component, OnInit } from '@angular/core';
import { MatDialogRef} from '@angular/material';
import { ComisionSeleccionada } from './comision-seleccionada.model';
import { FormBuilder } from '@angular/forms';
import { RegistroDeComisionesSeleccionadasService } from '../seleccion-de-materias-por-cursar/registro-de-comisiones-seleccionadas.service';

@Component({
	selector: 'app-seleccion-de-comision-dialogo',
	templateUrl: './seleccion-de-comision-dialogo.component.html',
	styleUrls: ['./seleccion-de-comision-dialogo.component.css']
})
export class SeleccionDeComisionDialogoComponent implements OnInit{
	materia: any;
	comisiones: any[];
	comisionActual: any;

	constructor(
		private registroComisionesService: RegistroDeComisionesSeleccionadasService,
		public dialogRef: MatDialogRef<SeleccionDeComisionDialogoComponent>,
		private fb: FormBuilder) {
	}

	ngOnInit() {
		this.comisiones =  this.materia.comisionesJson.sort(function (a, b) {
			return a.nombre.localeCompare(b.nombre);
		});;
	}

	enviarRegistro(comisionSeleccionada: ComisionSeleccionada) {
		this.dialogRef.close(comisionSeleccionada);
	}

	close() {
		this.dialogRef.close();
	}

	comisionSeleccionada(idComision) {
		const comision = this.comisiones.find(c => c.id == idComision);
		const registro = this.registroComisionesService.crearRegistroDeComisionSeleccionada(this.materia.id, comision);
		this.enviarRegistro(registro);
	}
}