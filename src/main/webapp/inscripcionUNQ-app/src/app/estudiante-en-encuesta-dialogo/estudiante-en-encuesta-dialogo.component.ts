import { HttpErrorResponse } from '@angular/common/http';
import { Component,OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { Carrera } from '../carreras/carrera.model';
import { RestService } from '../rest.service';
import { UtilesService } from '../utiles.service';
import { EstudianteWebService } from '../model/estudianteWebService.model';
import { DatosPersonalesEstudianteWebService } from '../model/datosPersonalesEstudianteWebService.model';

@Component({
	selector: 'app-estudiante-en-encuesta-dialogo',
	templateUrl: './estudiante-en-encuesta-dialogo.component.html'
})
export class EstudianteEnEncuestaDialogoComponent implements OnInit {
	estudianteSeleccionado;
	carrerasActuales: Carrera[];
	carrerasSeleccionadas: Carrera[] = [];
	checked = true;
	carreraChecked = false;
	form: FormGroup;
	hayCarrerasSelecconadas = false;
	encuesta;

	constructor(
		private fb: FormBuilder,
		private utilesService: UtilesService,
		private restService: RestService,
		private dialogRef: MatDialogRef<EstudianteEnEncuestaDialogoComponent>) {
	}

	ngOnInit() {
		this.encuesta = JSON.parse(localStorage.getItem('encuesta-seleccionada'));
		if (this.estudianteSeleccionado != null) {
			this.carrerasSeleccionadas = this.estudianteSeleccionado.carreras;
		}
		this.getCarreras();
		this.crearFormularioMateria();
		this.insertarInformacionDeLaMateriaEnFormulario();

	}

	getCarreras() {
		this.restService.getCarreras().subscribe(carreras => {
			this.carrerasActuales = carreras;
		},
			(err: HttpErrorResponse) => {
				this.utilesService.mostrarMensajeDeError(err);
			});
	}

	crearFormularioMateria() {
		this.form = this.fb.group({
			nombre: ['', Validators.required],
			apellido: ['', Validators.required],
			dni: ['', Validators.required],
			email: ['', [Validators.required, Validators.email]]
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
		if (this.estudianteSeleccionado != null) {
			this.form.setValue({
				'nombre': this.estudianteSeleccionado.nombre,
				'apellido': this.estudianteSeleccionado.apellido,
				'dni': this.estudianteSeleccionado.dni,
				'email': this.estudianteSeleccionado.email
			});
		}
	}

	guardar() {
		if (this.form.valid) {
			this.armarEstudiante();
		} else {
			this.utilesService.validateAllFormFields(this.form);
		}
	}

	armarEstudiante() {
		if (this.carrerasSeleccionadas.length > 0) {
			const { nombre, apellido, dni, email } = this.form.value;
			const datosPersonales = new DatosPersonalesEstudianteWebService(nombre, apellido, dni, email);
			const estudiante = new EstudianteWebService(datosPersonales,this.carrerasSeleccionadas);
			if (this.estudianteSeleccionado== null) {
				this.crearNuevoEstudiante(estudiante);
			} else {
				this.actualizarEstudiante(estudiante, this.estudianteSeleccionado.id);
			}
		} else {
			this.utilesService.mostrarMensaje('Debe seleccionar al menos una carrera');
		}
	}

	crearNuevoEstudiante(estudiante: EstudianteWebService) {
		this.restService.agregarNuevoEstudianteEnEncuesta(estudiante, this.encuesta.id)
			.subscribe(res => {
				const mensaje = 'Se creó el nuevo extudiante con exito';
				this.utilesService.mostrarMensaje(mensaje);
				this.cerrar();
			},
				(err: HttpErrorResponse) => {
					this.utilesService.mostrarMensajeDeError(err);
				});
	}

	actualizarEstudiante(estudiante: EstudianteWebService, idEstudiante) {
		estudiante.id = idEstudiante;
		this.actualizar(estudiante);
	}

	actualizar(estudiante: EstudianteWebService) {
		console.log(estudiante);
		this.restService.actualizarEstudianteEnEncuesta(estudiante)
			.subscribe(res => {
				const mensaje = 'Los datos del estudiante fueron actualizados con éxito';
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