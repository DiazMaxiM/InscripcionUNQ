import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { Carrera } from '../carreras/carrera.model';
import { UtilesService } from '../utiles.service';
import { RestService } from '../rest.service';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
	selector: 'app-carrera-dialogo',
	templateUrl: './carrera-dialogo.component.html',
	styleUrls: ['../dialogo-abm.component.css']
})
export class CarreraDialogoComponent implements OnInit {
	form: FormGroup;
	carrera: Carrera;
	checked = true;

	constructor(
		private fb: FormBuilder,
		private utilesService: UtilesService,
		private dialogRef: MatDialogRef<CarreraDialogoComponent>,
		private restService: RestService) {
	}

	ngOnInit() {
		this.crearFormularioCarrera();
		this.insertarInformacionDeLaCarreraEnFormulario();
	}

	crearFormularioCarrera() {
		this.form = this.fb.group({
			codigo: ['', Validators.required],
			descripcion: ['', Validators.required]
		});
	}

	insertarInformacionDeLaCarreraEnFormulario() {
		if (this.carrera != null) {
			this.form.setValue({
				'codigo': this.carrera.codigo,
				'descripcion': this.carrera.descripcion,
			});
			this.checked = this.carrera.habilitada;
		}
	}

	crearNuevaCarrera(carrera: Carrera) {
		this.restService.agregarNuevaCarrera(carrera)
			.subscribe(res => {
				const mensaje = 'La nueva carrera se creó con éxito';
				this.utilesService.mostrarMensaje(mensaje);
				this.cerrar();
			},
				(err: HttpErrorResponse) => {
					this.utilesService.mostrarMensajeDeError(err);
				});
	}

	actualizarCarreraSeleccionada(carrera: Carrera, idCarrera) {
		carrera.id = idCarrera;
		this.actualizarCarrera(carrera);
	}

	actualizarCarrera(carrera) {
		this.restService.actualizarInformacionCarrera(carrera)
			.subscribe(res => {
				const mensaje = 'Los datos de la carrera fueron actualizados con éxito';
				this.utilesService.mostrarMensaje(mensaje);
				this.cerrar();
			},
				(err: HttpErrorResponse) => {
					this.utilesService.mostrarMensajeDeError(err);
				});
	}

	guardar() {
		if (this.form.valid) {
			const { codigo, descripcion } = this.form.value;
			const carrera = new Carrera(codigo, descripcion, this.checked);
			if (this.carrera == null) {
				this.crearNuevaCarrera(carrera);
			} else {
				this.actualizarCarreraSeleccionada(carrera, this.carrera.id);
			}
		} else {
			this.utilesService.validateAllFormFields(this.form);
		}
	}

	cerrar() {
		this.dialogRef.close();
	}
}