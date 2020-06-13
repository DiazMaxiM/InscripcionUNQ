import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { Incidencia } from './incidencia.model';
import { UtilesService } from '../utiles.service';
import { RestService } from '../rest.service';

@Component({
	selector: 'app-incidencia-dialogo',
	templateUrl: './incidencia-dialogo.component.html',
	styleUrls: ['../dialogo-abm.component.css']
})
export class IncidenciaDialogoComponent implements OnInit {
	form: FormGroup;
	tipoIncidencia: String[] = [];

	constructor(
		private fb: FormBuilder,
		private utilesService: UtilesService,
		private dialogRef: MatDialogRef<IncidenciaDialogoComponent>,
		private restService: RestService) {
	}

	ngOnInit() {
		this.crearFormularioIncidencia();
		this.getTipoIncidencia();
	}

	getTipoIncidencia() {
		if (this.tipoIncidencia.length == 0) {
			this.restService.getTipoIncidencia().subscribe(incidencias => {
				this.tipoIncidencia = incidencias;
			});
		}
	}

	crearFormularioIncidencia() {
		this.form = this.fb.group({
			tipoIncidencia: ['', Validators.required],
			descripcion: ['', Validators.required],
			email: ['', [Validators.required, Validators.email]]
		});
	}

	guardar() {
		if (this.form.valid) {
			const { tipoIncidencia, descripcion, email } = this.form.value;
			const incidencia = new Incidencia(tipoIncidencia, descripcion, email);
			this.dialogRef.close(incidencia);
		} else{
       this.utilesService.validateAllFormFields(this.form);
		}
	}

	cerrar() {
		this.dialogRef.close();
	}
}