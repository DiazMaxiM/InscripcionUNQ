import { Component, OnInit } from '@angular/core';
import {  MatDialogRef } from '@angular/material';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { UtilesService } from '../utiles.service';
import { OfertaAcademica } from '../oferta-academica/oferta-academica.model';
import { Carrera } from '../carreras/carrera.model';
import { RestService } from '../rest.service';
import { Periodo } from '../periodos/periodo.model';
import { AppRutas } from '../app-rutas.model';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
	selector: 'app-oferta-academica-dialogo',
	templateUrl: './oferta-academica-dialogo.component.html',
	styleUrls: ['../dialogo-abm.component.css']
})
export class OfertaAcademicaDialogoComponent implements OnInit {
	form: FormGroup;
	ofertaSeleccionada: OfertaAcademica;
	checked = true;
	carreras: Carrera[];
	carreraBuscada;
	periodos: Periodo[];
	periodoBuscado;
	
	constructor(
		private fb: FormBuilder,
		private utilesService: UtilesService,
		private restService: RestService,
		private dialogRef: MatDialogRef<OfertaAcademicaDialogoComponent>) {
	}

	ngOnInit() {
		this.crearFormularioCarrera();
		this.insertarInformacionDeLaCarreraEnFormulario();
		this.getCarreras();
		this.getPeriodos();
		this.form.controls['periodo'].valueChanges.subscribe((term: FormGroup) => {
			this.periodoBuscado = term;
			
		});
		this.form.controls['carrera'].valueChanges.subscribe((term: FormGroup) => {
			this.carreraBuscada = term;
			
		});
	}


	irATarerasUsuario(mensaje) {
		this.cerrar();
		this.utilesService.mostrarMensajeYRedireccionar(mensaje, AppRutas.TAREAS_USUARIO);
	}

	guardarCarreras(carreras: Carrera[]) {
		if (carreras.length == 0) {
			const mensaje = 'No se encontraron carreras para la oferta';
			this.irATarerasUsuario(mensaje);
		}
		this.carreras = carreras;
	}

	getCarreras() {
		this.restService.getCarreras().subscribe(carreras => {
			this.guardarCarreras(carreras);
		},
			(err) => {
				this.utilesService.mostrarMensajeDeError(err);
			});
	}

	getPeriodos() {
		this.restService.getPeriodos().subscribe(periodos => {
			this.periodos = periodos;
			this.guardarPeriodos(periodos);
		},
			(err) => {
				this.utilesService.mostrarMensajeDeError(err);
			});
	}

	guardarPeriodos(periodos: Periodo[]) {
		if (periodos.length == 0) {
			const mensaje = 'No se encontraron períodos para la oferta';
			this.irATarerasUsuario(mensaje);
		} else {
			this.periodos = periodos;
		}
	}

	crearFormularioCarrera() {
		this.form = this.fb.group({
			descripcion: ['', Validators.required],
			carrera: ['', Validators.required],
			periodo: ['', Validators.required]
		});
	}

	insertarInformacionDeLaCarreraEnFormulario() {
		if (this.ofertaSeleccionada != null) {
			this.form.setValue({
				'descripcion': this.ofertaSeleccionada.descripcion,
				'carrera': this.ofertaSeleccionada.carrera.codigo,
				'periodo': this.ofertaSeleccionada.periodo.codigo
			});
			this.checked = this.ofertaSeleccionada.habilitada;
		}
	}

	guardar() {
		if (this.form.valid) {
			this.armarOferta();
		} else {
			this.utilesService.validateAllFormFields(this.form);
		}
	}

	armarOferta() {
		const { descripcion, periodo, carrera } = this.form.value;
		if (this.hayPeriodoValido(periodo) && this.hayCarreraValida(carrera)) {
			const periodoSeleccionado = this.utilesService.obtenerPeriodo(this.periodos, periodo);
			const carreraSeleccionada = this.utilesService.obtenerCarrera(this.carreras, carrera);
			const oferta = new OfertaAcademica(descripcion, this.checked, carreraSeleccionada, periodoSeleccionado);
			if (this.ofertaSeleccionada == null) {
				this.crearNuevaOferta(oferta);
			} else {
				this.actualizarOfertaSeleccionada(oferta, this.ofertaSeleccionada);
			}
		}
	}

	hayPeriodoValido(periodo) {
		return this.utilesService.periodoValido(this.periodos, periodo);
	}

	hayCarreraValida(carrera) {
		return this.utilesService.carreraValida(this.carreras, carrera);
	}

	cerrar() {
		this.dialogRef.close();
	}

	crearNuevaOferta(oferta: OfertaAcademica) {
		this.restService.crearNuevaOferta(oferta).subscribe(
			res => {
				const mensaje = "Se creó la nueva oferta académica con éxito";
				this.utilesService.mostrarMensaje(mensaje);
				this.cerrar();
			},
			(err: HttpErrorResponse) => {
				this.utilesService.mostrarMensajeDeError(err);
			}
		);
	}

	actualizarOfertaSeleccionada(oferta: OfertaAcademica, ofertaSeleccionada) {
		oferta.id = ofertaSeleccionada.id;
		oferta.nroComisionesCreadas = ofertaSeleccionada.nroComisionesCreadas;
		this.actualizarOferta(oferta);
	}

	actualizarOferta(oferta) {
		this.restService.actualizarInformacionDeOferta(oferta).subscribe(
			res => {
				const mensaje =
					"Los datos de la oferta académica fueron actualizados con éxito";
				this.utilesService.mostrarMensaje(mensaje);
				this.cerrar();
			},
			(err: HttpErrorResponse) => {
				this.utilesService.mostrarMensajeDeError(err);
			}
		);
	}

	cambiarEstado(oferta, evento) {
		oferta.habilitada = evento.checked;
		this.actualizarOferta(oferta);
	}
}