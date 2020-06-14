import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { UtilesService } from '../utiles.service';
import { RestService } from '../rest.service';
import { TipoIncidencia } from './tipo-incidencia.model';
import { AppMensajes } from '../app-mensajes.model';

@Component({
	selector: 'app-tipo-incidencia-dialogo',
	templateUrl: './tipo-incidencia-dialogo.component.html',
	styleUrls: ['../dialogo-abm.component.css']
})
export class TipoIncidenciaDialogoComponent implements OnInit {
	form: FormGroup;
	tipoDeIncidencia: TipoIncidencia;

	constructor(
		private fb: FormBuilder,
		private utilesService: UtilesService,
		private dialogRef: MatDialogRef<TipoIncidenciaDialogoComponent>,
		private restService: RestService) {
	}

	ngOnInit() {
		this.crearFormularioIncidencia();
		this.insertarDatosDelTipoDeIncidenciaEnFormulario();
	}

	insertarDatosDelTipoDeIncidenciaEnFormulario() {
		if (this.tipoDeIncidencia != null) {
			this.form.setValue({
				'descripcion': this.tipoDeIncidencia.descripcion,
			});
		}
	}

	crearFormularioIncidencia() {
		this.form = this.fb.group({
			descripcion: ['', Validators.required],
		});
	}

	guardar() {
		if (this.form.valid) {
			const {descripcion} = this.form.value;
			const tipoIncidencia = new TipoIncidencia(descripcion);
			this.guardarTipoIncidencia(tipoIncidencia);
		} else {
       this.utilesService.validateAllFormFields(this.form);
		}
	}

	guardarTipoIncidencia(tipoIncidencia) {
		if(this.tipoDeIncidencia != null) {
			this.actualizarTipoDeIncidencia(tipoIncidencia);
		} else {
			this.crearTipoIncidencia(tipoIncidencia);
		}
	}

	actualizarTipoDeIncidencia(tipoDeIncidencia: TipoIncidencia) {
		tipoDeIncidencia.id = this.tipoDeIncidencia.id;
		tipoDeIncidencia.estado = this.tipoDeIncidencia.estado;
		this.restService.actualizarTipoIncidencia(tipoDeIncidencia).subscribe(res => {
			this.utilesService.mostrarMensaje(AppMensajes.ACTUALIZACION_ENCUESTA_EXITOS0);
			this.cerrar();
		},
			(err) => {
				this.utilesService.mostrarMensajeDeError(err);
			});
	}
	
	crearTipoIncidencia(tipoDeIncidencia) {
		this.restService.agregarTipoIncidencia(tipoDeIncidencia).subscribe(res => {
			this.utilesService.mostrarMensaje(AppMensajes.CREACION_TIPO_INCIDENCIA_EXITOSO);
			this.cerrar();
		},
			(err) => {
				this.utilesService.mostrarMensajeDeError(err);
			});
	}

	cerrar() {
		this.dialogRef.close();
	}
}