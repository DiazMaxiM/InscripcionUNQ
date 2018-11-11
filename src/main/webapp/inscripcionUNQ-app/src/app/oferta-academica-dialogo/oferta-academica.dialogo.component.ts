import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef, MAT_DIALOG_SCROLL_STRATEGY_PROVIDER_FACTORY } from '@angular/material';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { DataDialogo } from './data-dialogo.model';
import { UtilesService } from '../utiles.service';
import { OfertaAcademica } from '../oferta-academica/oferta-academica.model';
import { Observable } from 'rxjs';
import { map, startWith } from 'rxjs/operators';
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
	filtroCarreras: Observable<Carrera[]>;
	periodos: Periodo[];
	filtroPeriodos: Observable<Periodo[]>;

	constructor(
		private fb: FormBuilder,
		private utilesService: UtilesService,
		private restService: RestService,
		private dialogRef: MatDialogRef<OfertaAcademicaDialogoComponent>,
		@Inject(MAT_DIALOG_DATA) public data: DataDialogo) {
		this.ofertaSeleccionada = data.oferta;
	}

	ngOnInit() {
		this.crearFormularioCarrera();
		this.insertarInformacionDeLaCarreraEnFormulario();
		this.getCarreras();
		this.getPeriodos();
	}

	crearFiltroCarreras() {
		this.filtroCarreras = this.form.controls['carrera'].valueChanges.pipe(
			startWith(''),
			map(val => this.filtrarCarrera(val))
		);
	}

	crearFiltroPeriodos() {
		this.filtroPeriodos = this.form.controls['periodo'].valueChanges.pipe(
			startWith(''),
			map(val => this.filtrarPeriodo(val))
		);
	}

	filtrarPeriodo(val: string): Periodo[] {
		return this.periodos.filter(option => {
			return option.codigo.toLowerCase().match(val.toLowerCase());
		});
	}

	filtrarCarrera(val: string): Carrera[] {
		return this.carreras.filter(option => {
			return option.descripcion.toLowerCase().match(val.toLowerCase());
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
		this.crearFiltroCarreras();
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
			this.crearFiltroPeriodos();
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