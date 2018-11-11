import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { UtilesService } from '../utiles.service';
import { RestService } from '../rest.service';
import { Periodo } from '../periodos/periodo.model';
import { Observable } from 'rxjs';
import { startWith, map } from 'rxjs/operators';

@Component({
	selector: 'app-seleccion-de-periodo-dialogo',
	templateUrl: './seleccion-de-periodo-dialogo.component.html',
	styleUrls: ['../dialogo-abm.component.css']
})

export class SeleccionDePeriodoDialogoComponent implements OnInit {
	form: FormGroup;
	periodos: Periodo[];
	filtroPeriodos: Observable<Periodo[]>;

	constructor(
		private fb: FormBuilder,
		private utilesService: UtilesService,
		private dialogRef: MatDialogRef<SeleccionDePeriodoDialogoComponent>,
		private restService: RestService) { }

	ngOnInit() {
		this.crearFormularioPeriodo();
		this.getPeriodos();
	}

	crearFormularioPeriodo() {
		this.form = this.fb.group({
			periodo: ['', Validators.required],
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
			const mensaje = 'No se encontraron períodos creados';
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

	guardar() {
		const { periodo } = this.form.value;
		if (this.form.valid && this.hayPeriodoValido(periodo)) {
			const periodoSeleccionado = this.utilesService.obtenerPeriodo(this.periodos, periodo);
			this.dialogRef.close(periodoSeleccionado.id);
		} else {
			this.utilesService.validateAllFormFields(this.form);
		}
	}
}