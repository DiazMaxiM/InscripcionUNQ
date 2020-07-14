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

	irAEstudiantesEnEncuesta(encuesta: Encuesta) {
		this.utilesService.activarDialogoCargando("Cargando información de los estudiantes");
		this.restService.getEstudiantesEnEncuesta(String(encuesta.id)).subscribe(
			estudiantes => {
				localStorage.setItem(
					"estudiantes-en-encuesta",
					JSON.stringify(estudiantes)
				);
				localStorage.setItem("encuesta-seleccionada", JSON.stringify(encuesta));
				this.utilesService.desactivarDialogoCargando();
				this.utilesService.irA("estudiantes-en-encuesta");
			},
			err => {
				this.utilesService.desactivarDialogoCargando();
				this.utilesService.mostrarMensajeDeError(err);
			}
		);
	}

	archivoSeleccionado(event){
		const archivo = <File> event.target.files[0];
        let reader = new FileReader();
        reader.onload = () => {
            // this 'text' is the content of the file
			var text = reader.result;
			this.guardarArchivo(text)
        }
		reader.readAsText(archivo);

	}

	guardarArchivo(text){
		this.restService.guardarArchivo(text).subscribe(() => {
			this.utilesService.mostrarMensaje("El archivo fue guardado con exito")
		},
			(err) => {
				this.utilesService.mostrarMensajeDeError(err);
		});
  }
}