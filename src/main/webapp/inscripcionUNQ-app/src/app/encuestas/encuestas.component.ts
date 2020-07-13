import { Component, OnInit } from '@angular/core';
import { UtilesService } from '../utiles.service';
import { RestService } from '../rest.service';
import { Encuesta } from '../encuesta-dialogo/encuesta.model';
import { DialogosService } from '../dialogos.service';
import { Estudiante } from '../informacion-del-usuario/estudiante.model';

@Component({
	selector: 'app-encuestas',
	templateUrl: './encuestas.component.html',
	styleUrls: ['../estilo-abm.component.css']
})

export class EncuestasComponent implements OnInit {
	encuestas: any[];
	encuestaBuscada;

	constructor(
		private restService: RestService,
		private utilesService: UtilesService,
		private dialogosService: DialogosService,
	) { }

	ngOnInit() {
		this.encuestas = JSON.parse(localStorage.getItem('encuestas'));
	}

	abrirDialogoParaAltaOModificacionEncuesta(encuesta?: Encuesta) {
		this.dialogosService
		.abrirDialogoEncuesta(encuesta)
		.subscribe(res => {
			this.getEncuestas();
		});
	}

	getEncuestas() {
		this.restService.getEncuestas().subscribe(encuestas => {
			this.encuestas = encuestas;
		},
			(err) => {
				this.utilesService.mostrarMensajeDeError(err);
			});
	}

	abrirDialogoDeOfertasAcademicas(encuesta: Encuesta) {
		this.dialogosService
		.abrirDialogoOfertaAcademicaDeEncuesta(encuesta)
		.subscribe(res => {
			this.getEncuestas();
		});
	}

	abrirDialogoParaDescargarReporte(encuesta: Encuesta) {
		this.dialogosService
		.abrirDialogoReportes(encuesta)
		.subscribe(res => {
			this.getEncuestas();
		});
	}

	abrirDialogoEstudiantesEnEncuesta(encuesta: Encuesta){
		this.dialogosService.abrirDialogoEstudiantesEnEncuesta(encuesta)

	}


	irAEstudiantesEnEncuesta(encuesta: Encuesta) {
		this.restService.getEstudiantesEnEncuesta(String(encuesta.id)).subscribe(
			estudiantes => {
				localStorage.setItem(
					"estudiantes-en-encuesta",
					JSON.stringify(estudiantes)
				);
				localStorage.setItem("encuesta-seleccionada", JSON.stringify(encuesta));
				this.utilesService.irA("estudiantes-en-encuesta");
			},
			err => {
				this.utilesService.mostrarMensajeDeError(err);
			}
		);
	}
}