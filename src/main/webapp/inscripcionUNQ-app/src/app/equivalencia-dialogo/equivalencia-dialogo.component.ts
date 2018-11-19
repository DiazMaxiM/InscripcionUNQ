import { Component, OnInit } from '@angular/core';
import { UtilesService } from '../utiles.service';
import { RestService } from '../rest.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Materia } from '../materias/materia.model';
import { MatDialogRef} from '@angular/material';
import { Equivalencia } from '../equivalencias/equivalencia.model';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { AppMensajes } from '../app-mensajes.model';
import { AppRutas } from '../app-rutas.model';

@Component({
	selector: 'app-equivalencia-dialogo',
	templateUrl: './equivalencia-dialogo.component.html',
	styleUrls: ['../dialogo-abm.component.css']
})

export class EquivalenciaDialogoComponent implements OnInit {
	materias: Materia[];
	materiasOrigen: Materia[];
	materiasDestino: Materia[];
	form: FormGroup;
	equivalencia: Equivalencia;
	materiaOrigen;
	materiaDestino;

	constructor(
		private utilesService: UtilesService,
		private restService: RestService,
		private fb: FormBuilder,
		private dialogRef: MatDialogRef<EquivalenciaDialogoComponent>) {
	}

	ngOnInit() {
		this.getMaterias();
		this.crearFormularioEquivalencia();
		this.form.controls['materiaOrigen'].valueChanges.subscribe((term: FormGroup) => {
			this.materiaOrigen = term;
			this.eliminarMateriaEnMateriasDestino(term);
		});
		this.form.controls['materiaDestino'].valueChanges.subscribe((term: FormGroup) => {
			this.materiaDestino = term;
			this.eliminarMateriaEnMateriasOrigen(term);
		});
	}

	insertarInformacionDeEquivalenciaEnFormulario() {
		if (this.equivalencia != null) {
			this.form.setValue({
				'materiaOrigen': this.equivalencia.materiaOrigen.nombre,
				'materiaDestino': this.equivalencia.materiaDestino.nombre
			});
		}
	}

	crearFormularioEquivalencia() {
		this.form = this.fb.group({
			materiaOrigen: ['', Validators.required],
			materiaDestino: ['', Validators.required],
		});
	}

	getMaterias() {
		this.restService.getMaterias().subscribe(materias => {
			this.guardarMaterias(materias);
		},
			(err: HttpErrorResponse) => {
				this.utilesService.mostrarMensajeDeError(err);
			});
	}

	guardarMaterias(materias: Materia[]) {
		if (materias.length == 0) {
			this.irATarerasUsuario(AppMensajes.N0_HAY_MATERIAS_CARGADAS);

		} else {
			this.materias = materias;
			this.materiasOrigen = materias;
			this.materiasDestino = materias;
			this.insertarInformacionDeEquivalenciaEnFormulario();
		}
	}

	irATarerasUsuario(mensaje) {
		this.cerrar();
		this.utilesService.mostrarMensajeYRedireccionar(mensaje, AppRutas.TAREAS_USUARIO);
	}

	guardar() {
		if (this.form.valid) {
			this.armarEquivalencia();
		} else {
			this.utilesService.validateAllFormFields(this.form);
		}
	}

	armarEquivalencia() {
		const { materiaOrigen, materiaDestino } = this.form.value;
		if (this.hayMateriaValida(materiaOrigen) && this.hayMateriaValida(materiaDestino)) {
			const equivalencia = new Equivalencia();
			equivalencia.materiaOrigen = this.utilesService.obtenerMateria(this.materias, materiaOrigen);
			equivalencia.materiaDestino = this.utilesService.obtenerMateria(this.materias, materiaDestino);
			this.guardarEquivalencia(equivalencia);
		}
	}

	hayMateriaValida(materia) {
		return this.utilesService.materiaValida(this.materias, materia);
	}

	cerrar() {
		this.dialogRef.close();
	}

	eliminarMateriaEnMateriasOrigen(materia) {
		this.materiasOrigen = this.eliminarMateria(materia);
	}

	eliminarMateriaEnMateriasDestino(materia) {
		this.materiasDestino = this.eliminarMateria(materia);
	}

	eliminarMateria(nombreMateria){
		return this.materias.filter(materia => materia.nombre != nombreMateria);
	}

	guardarEquivalencia(equivalencia) {
		if (this.equivalencia == null) {
			this.crearNuevaEquivalencia(equivalencia);
		} else {
			this.actualizarEquivalencia(equivalencia);
		}

	}
	actualizarEquivalencia(equivalencia: Equivalencia) {
		equivalencia.id = this.equivalencia.id;
		this.restService.actualizarEquivalencia(equivalencia)
			.subscribe(res => {
				const mensaje = 'La equivalencia fue actualizada con éxito';
				this.utilesService.mostrarMensaje(mensaje);
				this.cerrar();
			},
				(err: HttpErrorResponse) => {
					this.utilesService.mostrarMensajeDeError(err);
				});
	}

	crearNuevaEquivalencia(equivalencia: Equivalencia) {
		this.restService.agregarNuevaEquivalencia(equivalencia)
			.subscribe(res => {
				const mensaje = 'La nueva equivalencia se creó con éxito';
				this.utilesService.mostrarMensaje(mensaje);
				this.cerrar();
			},
				(err: HttpErrorResponse) => {
					this.utilesService.mostrarMensajeDeError(err);
				});
	}



}
