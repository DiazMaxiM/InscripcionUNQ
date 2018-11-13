import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import { FormBuilder, Validators, FormGroup, FormControl } from '@angular/forms';
import { UtilesService } from '../utiles.service';
import { RestService } from '../rest.service';
import { Periodo } from '../periodos/periodo.model';
import { Observable } from 'rxjs';
import { AppMensajes } from '../app-mensajes.model';
import { startWith, map } from 'rxjs/operators';
import { Encuesta } from './encuesta.model';
import { Fecha } from './fecha.model';
import { DataDialogo } from './data-dialogo.model';
import * as moment from 'moment';
import { OfertaAcademica } from '../oferta-academica/oferta-academica.model';

@Component({
	selector: 'app-encuesta-dialogo',
	templateUrl: './encuesta-dialogo.component.html',
	styleUrls: ['../dialogo-abm.component.css']
})

export class EncuestaDialogoComponent implements OnInit {
	form: FormGroup;
	periodos: Periodo[];
	filtroPeriodos: Observable<Periodo[]>;
	periodoControl = new FormControl();
	encuestaSeleccionada: Encuesta;
	ofertas: OfertaAcademica[];
	seleccionaOfertasAcademicas;
	ofertasSeleccionados: OfertaAcademica[] = [];
	nuevaEncuesta: Encuesta;
	modificaPeriodo;

	constructor(
		private fb: FormBuilder,
		private utilesService: UtilesService,
		private dialogRef: MatDialogRef<EncuestaDialogoComponent>,
		private restService: RestService,
		@Inject(MAT_DIALOG_DATA) public data: DataDialogo) {
		this.encuestaSeleccionada = data.encuesta;
	}

	ngOnInit() {
		this.crearFormularioComision();
		this.getPeriodos();
		this.insertarInformacionDeLaEncuesta();
	}

	crearFormularioComision() {
		this.form = this.fb.group({
			nombre: ['', Validators.required],
			periodo: ['', Validators.required],
			fechaDeComienzo: ['', Validators.required],
			fechaDeFinalizacion: ['', Validators.required],
			horarioComienzo: [{ hour: 0, minute: 0 }, Validators.required],
			horarioFinalizacion: [{hour: 0, minute: 0}, Validators.required]
		}, { validator: this.checkDates });
	}

	checkDates(group: FormGroup) {
		if (group.controls.fechaDeFinalizacion.value < group.controls.fechaDeComienzo.value) {
			return group.controls.fechaDeFinalizacion.setErrors({ notValid: true });
		}
	}

	insertarInformacionDeLaEncuesta() {
		if (this.encuestaSeleccionada != null) {
			this.form.setValue({
				'nombre': this.encuestaSeleccionada.nombre,
				'periodo': this.encuestaSeleccionada.periodo.codigo,
				'fechaDeComienzo': this.crearFecha(this.encuestaSeleccionada.fechaComienzo),
				'fechaDeFinalizacion': this.crearFecha(this.encuestaSeleccionada.fechaFin),
				'horarioComienzo': this.utilesService.armarHorario(this.encuestaSeleccionada.fechaComienzo.horario),
				'horarioFinalizacion': this.utilesService.armarHorario(this.encuestaSeleccionada.fechaFin.horario)
			});
			this.modificaPeriodo = true;
		}
	}

	crearFecha(fecha: Fecha) {
		return moment({
			year: fecha.anho,
			month: fecha.mes - 1,
			date: fecha.dia
		});
	}

	hayPeriodoValido(periodo) {
		return this.utilesService.periodoValido(this.periodos, periodo);
	}

	cerrar() {
		this.dialogRef.close();
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
			const mensaje = 'No se encontraron períodos para la comisión';
		}
		this.periodos = periodos;
		this.crearFiltroPeriodos();
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

	soloNumero(evento) {
		return this.utilesService.soloNumero(evento);
	}

	guardar() {
		if (this.form.valid) {
			this.crearEncuesta();
		} else {
			this.utilesService.validateAllFormFields(this.form);
		}
	}

	crearEncuesta() {
		const { nombre, periodo, fechaDeComienzo, fechaDeFinalizacion, horarioComienzo, horarioFinalizacion } = this.form.value;
		if (this.hayPeriodoValido(periodo)) {

			const encuesta = new Encuesta();
			encuesta.nombre = nombre;
			encuesta.periodo = this.utilesService.obtenerPeriodo(this.periodos, periodo);
			encuesta.fechaComienzo = this.crearFechaConHora(fechaDeComienzo._i, horarioComienzo);
			encuesta.fechaFin = this.crearFechaConHora(fechaDeFinalizacion._i, horarioFinalizacion);
			this.guardarEncuesta(encuesta);
		}
	}

	guardarEncuesta(encuesta) {
		if (this.encuestaSeleccionada != null) {
			this.actualizarEncuesta(encuesta);
		} else {
			this.crearNuevaEncuesta(encuesta);
		}
	}

	crearNuevaEncuesta(encuesta) {
		this.seleccionaOfertasAcademicas = true;
		this.nuevaEncuesta = encuesta;
		this.getOfertasEnPeriodo(encuesta);

	}

	guardarNuevaEncuesta(encuesta) {
		this.restService.crearEncuesta(encuesta).subscribe(rest => {
			this.utilesService.mostrarMensaje(AppMensajes.CREACION_ENCUESTA_EXITOSO);
			this.cerrar();
		},
			(err) => {
				this.utilesService.mostrarMensajeDeError(err);
			});
	}

	getOfertasEnPeriodo(encuesta) {
		this.restService.getOfertasEnPeriodo(encuesta.periodo.id).subscribe(ofertas => {
			this.guardarOfertas(ofertas, encuesta);
		},
			(err) => {
				this.utilesService.mostrarMensajeDeError(err);
			});
	}

	guardarOfertas(ofertas: OfertaAcademica[], encuesta) {
		if (ofertas.length == 0) {
			this.utilesService.mostrarMensaje(AppMensajes.N0_HAY_OFERTAS_EN_PERIODO + encuesta.periodo.codigo);
			this.seleccionaOfertasAcademicas = false;
		} else {
			this.ofertas = ofertas;
		}
	}

	onChange(oferta, $event) {
		if ($event.checked) {
			this.ofertasSeleccionados.push(oferta);
		} else {
			this.ofertasSeleccionados.forEach(ofertaSeleccionada => {
				if (ofertaSeleccionada.id == oferta.id) {
					this.ofertasSeleccionados.splice(this.ofertasSeleccionados.indexOf(ofertaSeleccionada), 1);
				}
			});
		}
	}

	actualizarEncuesta(encuesta: Encuesta) {
		encuesta.id = this.encuestaSeleccionada.id;
		console.log(encuesta);
		this.restService.actualizarEncuesta(encuesta).subscribe(rest => {
			this.utilesService.mostrarMensaje(AppMensajes.ACTUALIZACION_ENCUESTA_EXITOS0);
			this.cerrar();
		},
			(err) => {
				this.utilesService.mostrarMensajeDeError(err);
			});
	}

	keyDown(event) {
		const allowedRegex = /[0-9\/]/g;

		if (!event.key.match(allowedRegex)) {
			event.preventDefault();
		}
	}

	crearFechaConHora(fecha, horario) {
		const nuevaFecha = new Fecha();
		nuevaFecha.dia = fecha.date;
		nuevaFecha.mes = fecha.month + 1;
		nuevaFecha.anho = fecha.year;
		nuevaFecha.horario = horario;
		return nuevaFecha;
	}

	atras() {
		this.seleccionaOfertasAcademicas = false;
		this.ofertasSeleccionados = [];
	}

	enviarEncuesta() {
		if (this.ofertasSeleccionados.length == 0) {
			this.utilesService.mostrarMensaje('Debe seleccionar al menos una oferta académica');
		} else {
			this.nuevaEncuesta.ofertasAcademicas = this.ofertasSeleccionados;
			this.guardarNuevaEncuesta(this.nuevaEncuesta);
		}
	}
}