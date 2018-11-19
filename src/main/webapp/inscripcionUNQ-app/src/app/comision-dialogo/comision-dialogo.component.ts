import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material';
import { FormBuilder, Validators, FormGroup, FormControl } from '@angular/forms';
import { Comision } from '../comisiones-de-oferta/comision.model';
import { UtilesService } from '../utiles.service';
import { Materia } from '../materias/materia.model';
import { Observable } from 'rxjs';
import { startWith, map } from 'rxjs/operators';
import { RestService } from '../rest.service';
import { Periodo } from '../periodos/periodo.model';
import { AppRutas } from '../app-rutas.model';
import { HorarioComision } from './horario-comision.model';
import { AppMensajes } from '../app-mensajes.model';
import { RegistroDeComisionesSeleccionadasService } from '../seleccion-de-materias-por-cursar/registro-de-comisiones-seleccionadas.service';
import { Horario } from '../seleccion-de-comision-dialogo/horario.model';

@Component({
	selector: 'app-comision-dialogo',
	templateUrl: './comision-dialogo.component.html',
	styleUrls: ['../dialogo-abm.component.css']
})

export class ComisionDialogoComponent implements OnInit {
	form: FormGroup;
	formHorario: FormGroup;
	comisionSeleccionada: Comision;
	materias: Materia[];
	filtroMaterias: Observable<Materia[]>;
	mostrarformularioParaHorarios = false;
	mostrarTablaHorarios = false;
	dias;
	periodos: Periodo[];
	filtroPeriodos: Observable<Periodo[]>;
	horarios = [];
	horarioAEditar: HorarioComision;
	mostrarGuardarComision = true;
	periodoControl = new FormControl();
	comisiones: Comision[];
	mostrarComisiones;

	constructor(
		private fb: FormBuilder,
		private utilesService: UtilesService,
		private dialogRef: MatDialogRef<ComisionDialogoComponent>,
		private restService: RestService,
		private controlHorarioService: RegistroDeComisionesSeleccionadasService) {
	}

	ngOnInit() {
		this.crearFormularioComision();
		this.insertarInformacionDeComisionEnFormularioYCrearTablaDeHorarios();
		this.getMaterias();
		this.crearFormularioParaHorario();
		this.getDias();
		this.getPeriodos();
	}

	getDias() {
		this.restService.getDias().subscribe(dias => {
			this.dias = dias;
		});
	}

	crearFiltroMaterias() {
		this.filtroMaterias = this.form.controls['materia'].valueChanges.pipe(
			startWith(''),
			map(val => this.filtrarMaterias(val))
		);
	}

	filtrarMaterias(val: string): Materia[] {
		return this.materias.filter(option => {
			return option.nombre.toLowerCase().match(val.toLowerCase());
		});
	}

	getMaterias() {
		this.restService.getMaterias().subscribe(materias => {
			this.materias = materias;
			this.crearFiltroMaterias();
		},
			(err) => {
				this.utilesService.mostrarMensajeDeError(err);
			});
	}

	crearFormularioParaHorario() {
		this.formHorario = this.fb.group({
			dia: ['', Validators.required],
			horarioComienzo: ['', Validators.required],
			duracion: ['', Validators.required]
		});
	}

	crearFormularioComision() {
		this.form = this.fb.group({
			nombre: ['', Validators.required],
			cupo: ['', Validators.required],
			materia: ['', Validators.required],
			periodo: ['', Validators.required],
		});
	}

	insertarInformacionDeComisionEnFormularioYCrearTablaDeHorarios() {
		if (this.comisionSeleccionada != null) {
			this.form.setValue({
				'nombre': this.comisionSeleccionada.nombre,
				'cupo': this.comisionSeleccionada.cupo,
				'materia': this.comisionSeleccionada.nombreMateria,
				'periodo': this.comisionSeleccionada.periodo.codigo
			});
			this.crearTablaConHorarios(this.comisionSeleccionada.horarioJson);
		}
	}

	crearTablaConHorarios(horarioJson: HorarioComision[]) {
		for (const horario of horarioJson) {
			this.agregarHorario(horario.dia, horario.horaComienzo, horario.duracion);
			this.mostrarHorariosSeleccionados();
		}
	}

	hayMateriaValida(materia) {
		return this.utilesService.materiaValida(this.materias, materia)
	}

	hayPeriodoValido(periodo) {
		return this.utilesService.periodoValido(this.periodos, periodo);
	}

	hayHorariosSeleccionados() {
		if (this.horarios.length == 0) {
			this.utilesService.mostrarMensaje(AppMensajes.NO_HAY_HORARIOS_CARGADOS);
			return false;
		} else {
			return true;
		}
	}

	cerrar() {
		this.dialogRef.close();
	}

	mostarFormularioHorarios() {
		this.mostrarformularioParaHorarios = true;
		this.mostrarTablaHorarios = false;
		this.mostrarGuardarComision = false;
	}

	mostrarHorariosSeleccionados() {
		if (this.horarios.length > 0) {
			this.mostrarTablaHorarios = true;
		} else {
			this.mostrarTablaHorarios = false;
		}
		this.mostrarformularioParaHorarios = false;
		this.mostrarGuardarComision = true;
	}

	guardarHorario() {
		if (this.formHorario.valid) {
			const { dia, horarioComienzo, duracion } = this.formHorario.value;
			const hora = this.utilesService.armarHorarioParaServidor(horarioComienzo);
			this.armarHorarioComision(dia, hora, duracion);
			this.mostrarHorariosSeleccionados();
			this.formHorario.reset();
		} else {
			this.utilesService.validateAllFormFields(this.formHorario);
		}
	}
	
	armarHorarioComision(dia, horarioComienzo, duracion) {
		const horario = new HorarioComision(dia, horarioComienzo, duracion);
		if (!this.esHorarioSuperpuesto(horario)) {
		  this.agregarHorario(dia,horarioComienzo,duracion);
		} else {
			this.utilesService.mostrarMensaje('El horario que intenta agregar genera superposición horaria');
		}
	}

	esHorarioSuperpuesto(horario) {
		const horarios = this.horarios.filter(horarioDeLaComision => horarioDeLaComision.dia == horario.dia 
			&& horarioDeLaComision != this.horarioAEditar);
		let haySuperposicion = false;
		for (const horarioOcupado of horarios) {
				haySuperposicion = haySuperposicion || this.haySuperposicionHoraria(horarioOcupado, horario);
		}
		console.log(horarios);
		return haySuperposicion;
	}

	haySuperposicionHoraria(horarioOcupado, horarioAOcupar) {
		const dateHorarioAOcupar = this.armarHorario(horarioAOcupar);
	  const dateHorarioOcupado = this.armarHorario(horarioOcupado);
		return this.controlHorarioService.esHorarioSuperpuesto(dateHorarioOcupado, dateHorarioAOcupar);
	}
	
	armarHorario(horario: HorarioComision) {
		const horarioComienzo = this.controlHorarioService.nuevoHorario(horario.horaComienzo);
		const horarioFinalizacion = this.armarHorarioFinalizacion(horario);
		return new Horario(horario.dia, horarioComienzo, horarioFinalizacion, horario.duracion);
	}

	armarHorarioFinalizacion(horario: HorarioComision) {
	 const nuevoHorario: Date = this.controlHorarioService.nuevoHorario(horario.horaComienzo);
	 nuevoHorario.setHours(nuevoHorario.getHours() + horario.duracion);
	 nuevoHorario.setMinutes(nuevoHorario.getMinutes() - 1);
	 console.log(nuevoHorario);
	 return nuevoHorario;
	}


	agregarHorario(dia, horarioComienzo, duracion) {
		const horario = new HorarioComision(dia, horarioComienzo, duracion);
		if (this.horarioAEditar != null) {
			this.horarios[this.horarios.indexOf(this.horarioAEditar)] = horario;
			this.horarioAEditar = null;
		} else {
			this.horarios.push(horario);
		}
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
			this.irATarerasUsuario(mensaje);
		}
		this.periodos = periodos;
		this.crearFiltroPeriodos();
	}

	irATarerasUsuario(mensaje) {
		this.cerrar();
		this.utilesService.mostrarMensajeYRedireccionar(mensaje, AppRutas.TAREAS_USUARIO);
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

	editarHorarip(horario: HorarioComision) {
		this.horarioAEditar = horario;
		this.formHorario.setValue({
			'dia': horario.dia,
			'duracion': horario.duracion,
			'horarioComienzo': this.utilesService.armarHorario(horario.horaComienzo),
		});
		this.mostarFormularioHorarios();
	}

	cancelarHorario() {
		this.formHorario.reset();
		this.mostrarHorariosSeleccionados();
	}

	eliminarHorario(horario) {
		const index = this.horarios.indexOf(horario);
		this.horarios.splice(index, 1);
		this.mostrarHorariosSeleccionados();
	}

	soloNumero(evento) {
		return this.utilesService.soloNumero(evento);
	}

	guardar() {
		if (this.form.valid) {
			this.armarComision();
		} else {
			this.utilesService.validateAllFormFields(this.form);
		}
	}

	armarComision() {
		const { nombre, cupo, materia, periodo } = this.form.value;
		if (this.hayMateriaValida(materia) && this.hayPeriodoValido(periodo) && this.hayHorariosSeleccionados()) {
			const comision = new Comision();
			comision.nombre = nombre;
			comision.cupo = cupo;
			comision.materia = this.utilesService.obtenerMateria(this.materias, materia);
			comision.periodo = this.utilesService.obtenerPeriodo(this.periodos, periodo);
			comision.horarioJson = this.horarios;
			this.guardarComision(comision);
		}
	}

	guardarComision(comision: Comision) {
		if (this.comisionSeleccionada != null) {
			this.guardarComisionModificada(comision);
		} else {
			this.guardarNuevaComision(comision);
		}
	}

	guardarComisionModificada(comision: Comision) {
		comision.id = this.comisionSeleccionada.id;
		this.restService.actualizarInformacionDeComision(comision).subscribe(rest => {
			const mensaje = 'Los datos de la comisión fueron actualizados con éxito';
			this.utilesService.mostrarMensaje(mensaje);
			this.cerrar();
		},
			(err) => {
				this.utilesService.mostrarMensajeDeError(err);
			});
	}

	guardarNuevaComision(comision: Comision) {
		this.restService.crearNuevaComision(comision).subscribe(rest => {
			const mensaje = 'Se creó la nueva comisión con éxito';
			this.utilesService.mostrarMensaje(mensaje);
			this.cerrar();
		},
			(err) => {
				this.utilesService.mostrarMensajeDeError(err);
			});
	}

	getComisionesEnPeriodo(periodo) {
		this.restService.getComisionesEnPeriodo(periodo.id).subscribe(comisiones => {
			this.guardarComisiones(comisiones);
		},
			(err) => {
				this.utilesService.mostrarMensajeDeError(err);
			});
	}

	guardarComisiones(comisiones) {
		if (comisiones.length == 0) {
			this.utilesService.mostrarMensaje(AppMensajes.NO_SE_ENCONTRARON_COMISIONES_EN_PERIODO);
			this.mostrarComisiones = false;
			this.comisiones = [];
		} else {
			this.comisiones = this.utilesService.ordenarComisionesPorNombre(comisiones);
			this.mostrarComisiones = true;
		}
	}
}