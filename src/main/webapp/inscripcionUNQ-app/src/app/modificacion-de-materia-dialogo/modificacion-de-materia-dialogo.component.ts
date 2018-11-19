import { HttpErrorResponse } from '@angular/common/http';
import { Component,OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material';
import { Carrera } from '../carreras/carrera.model';
import { Materia } from '../materias/materia.model';
import { RestService } from '../rest.service';
import { UtilesService } from '../utiles.service';

@Component({
	selector: 'app-modificacion-de-materia-dialogo',
	templateUrl: './modificacion-de-materia-dialogo.component.html',
	styleUrls: ['./modificacion-de-materia-dialogo.component.css']
})
export class ModificacionDeMateriaDialogoComponent implements OnInit {
	materia: Materia;
	carreras: Carrera[];
	carrerasSeleccionadas: Carrera[] = [];
	checked = true;
	carreraChecked = false;
	form: FormGroup;
	hayCarrerasSelecconadas = false;

	constructor(
		private fb: FormBuilder,
		private utilesService: UtilesService,
		private restService: RestService,
		private dialogRef: MatDialogRef<ModificacionDeMateriaDialogoComponent>) {
	}

	ngOnInit() {
		if (this.materia != null) {
			this.carrerasSeleccionadas = this.materia.carreras;
		}
		this.getCarreras();
		this.crearFormularioMateria();
		this.insertarInformacionDeLaMateriaEnFormulario();

	}

	getCarreras() {
		this.restService.getCarreras().subscribe(carreras => {
			this.carreras = carreras;
		},
			(err: HttpErrorResponse) => {
				this.utilesService.mostrarMensajeDeError(err);
			});
	}

	crearFormularioMateria() {
		this.form = this.fb.group({
			codigo: ['', Validators.required],
			nombre: ['', Validators.required],
			horas: ['', Validators.required],
			creditos: ['', Validators.required]
		});
	}

	onChange(carrera, $event) {
		if ($event.checked) {
			this.carrerasSeleccionadas.push(carrera);
		} else {
			this.carrerasSeleccionadas.forEach(carreraSeleccionada => {
				if (carrera.id == carreraSeleccionada.id) {
					this.carrerasSeleccionadas.splice(this.carrerasSeleccionadas.indexOf(carreraSeleccionada), 1);
				}
			});
		}
	}

	verificarSeleccion(carrera) {
		this.carreraChecked = false;
		this.carrerasSeleccionadas.forEach(carreraSeleccionada => {
			if (carrera.id == carreraSeleccionada.id) {
				this.carreraChecked = true;
			}
		});
		return this.carreraChecked;
	}

	insertarInformacionDeLaMateriaEnFormulario() {
		if (this.materia != null) {
			this.form.setValue({
				'codigo': this.materia.codigo,
				'nombre': this.materia.nombre,
				'horas': this.materia.horas,
				'creditos': this.materia.creditos
			});
			this.checked = this.materia.estado;
		}
	}

	guardar() {
		if (this.form.valid) {
			this.armarMateria();
		} else {
			this.utilesService.validateAllFormFields(this.form);
		}
	}

	armarMateria() {
		if (this.carrerasSeleccionadas.length > 0) {
			const { codigo, nombre, horas, creditos } = this.form.value;
			const materia = new Materia(codigo, nombre, this.carrerasSeleccionadas, this.checked, horas, creditos);
			if (this.materia == null) {
				this.crearNuevaMateria(materia);
			} else {
				this.actualizarMateriaSeleccionada(materia, this.materia.id);
			}
		} else {
			this.utilesService.mostrarMensaje('Debe seleccionar al menos una carrera');
		}
	}

	crearNuevaMateria(materia: Materia) {
		this.restService.agregarNuevaMateria(materia)
			.subscribe(res => {
				const mensaje = 'Se creó la nueva materia con éxito';
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

	soloNumero(evento) {
		return this.utilesService.soloNumero(evento);
	}
}