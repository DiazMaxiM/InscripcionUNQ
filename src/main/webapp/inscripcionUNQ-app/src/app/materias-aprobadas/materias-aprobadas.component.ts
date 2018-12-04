import { Component, OnInit} from '@angular/core';
import { RestService } from '../rest.service';
import { MateriaEstudiante } from './materia-estudiante.model';
import { UtilesService } from '../utiles.service';

@Component({
	selector: 'app-materias-aprobadas',
	templateUrl: './materias-aprobadas.component.html',
	styleUrls: ['./materias-aprobadas.component.css']
})
export class MateriasAprobadasComponent implements OnInit {

	materias: MateriaEstudiante[] = []
	materiasAprobadas: MateriaEstudiante[] = [];
	idEstudiante: string;
	materiaBuscada;
	agregaMaterias: boolean;
	nuevasMateriasAprobadas:  MateriaEstudiante[] = [];

	constructor(
		private restService: RestService,
		private utilesService: UtilesService
	) { }

	ngOnInit() {
		this.idEstudiante = localStorage.getItem('idEstudiante');
		this.getMaterias();
	}

	getMaterias() {
		this.restService.getMateriasAprobadas(this.idEstudiante)
			.subscribe(materias => {
				 this.materiasAprobadas = materias.filter(materia => materia.aprobada);
				 this.materias = materias.filter(materia => !materia.aprobada);
			}, (err) => {
				this.utilesService.mostrarMensajeDeError(err);
			});
	}

	actualizarMaterias() {
		this.utilesService.activarDialogoCargando();
		this.restService.actualizarMateriasAprobadas(this.idEstudiante, this.materiasAprobadas)
			.subscribe(res => {
				window.scroll(0,0);
				this.utilesService.desactivarDialogoCargandoYRedireccionar('materias-por-cursar');
			}, (err) => {
				this.utilesService.mostrarMensajeDeError(err);
			});
	}

	seleccionarMateria(id) {
		this.materiasAprobadas = this.actualizarListadoMateria(id, this.materiasAprobadas);
	}

	mostrarAgregarMaterias() {
		window.scroll(0,0);
    this.agregaMaterias = true;
	}
	ocultarAgregarMaterias() {
		window.scroll(0,0);
		this.agregaMaterias = false;
	}

	agregarMateriaAprobada(id) {
		this.materias = this.actualizarListadoMateria(id, this.materias);

	}

	actualizarListadoMateria(id, materias) {
		const result = [];
		for (const i in materias) {
			if (materias[i].id == id) {
				result.push({
					'id': materias[i].id,
					'codigo': materias[i].codigo,
					'nombre': materias[i].nombre,
					'aprobada': !materias[i].aprobada
				});
			} else {
				result.push(materias[i]);
			}
		}
		return result;
	}

	actualizarMateriasAprobadas() {
		 const materiasAprobadas = this.materias.filter(materia => materia.aprobada);
		 if(materiasAprobadas.length > 0){
			materiasAprobadas.forEach(materia => this.agregarMateria(materia));
			this.ocultarAgregarMaterias();
		 } else {
			 this.utilesService.mostrarMensaje('No ha seleccionado ninguna materia como aprobada');
		 }
	}

	agregarMateria(materia) {
		this.materiasAprobadas.push(materia);
		const index = this.materias.indexOf(materia);
    this.materias.splice(index, 1);
	}
}