import { Component, OnInit } from '@angular/core';
import { RestService } from '../rest.service';
import { UtilesService } from '../utiles.service';
import { DialogosService } from '../dialogos.service';
import { Encuesta } from '../encuesta-dialogo/encuesta.model';

@Component({
	selector: 'app-estudiantes-en-encuesta',
	templateUrl: './estudiantes-en-encuesta.component.html',
	styleUrls: ['../estilo-abm.component.css']
})

export class EstudiantesEnEncuestaComponent implements OnInit {
	estudiantes;
	encuesta: Encuesta;
	estudianteBuscado;

	constructor(
		private restService: RestService,
		private utilesService: UtilesService,
		private dialogosService: DialogosService
	) { }

	ngOnInit() {

		this.encuesta = JSON.parse(localStorage.getItem('encuesta-seleccionada'));
		this.estudiantes = this.utilesService.ordenarEstudiantesPorDNI(
			JSON.parse(localStorage.getItem('estudiantes-en-encuesta')));
			
	}

	abrirDialogoParaAltaOModificacionEstudiante(estudiante?){
		this.dialogosService.abrirDialogoEstudiantesEnEncuesta(estudiante).subscribe(res => {
			this.getEstudiantesEnEncuesta();
		});
	}

	getEstudiantesEnEncuesta(){
		this.restService.getEstudiantesEnEncuesta(String(this.encuesta.id)).subscribe(
			estudiantes => {
	            this.estudiantes = this.utilesService.ordenarEstudiantesPorDNI(estudiantes);
			},
			err => {
				this.utilesService.mostrarMensajeDeError(err);
			});
	}

}