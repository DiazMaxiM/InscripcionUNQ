import { Component, OnInit } from '@angular/core';
import { RestService } from '../rest.service';
import { UtilesService } from '../utiles.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Equivalencia } from './equivalencia.model';
import { DialogosService } from '../dialogos.service';

@Component({
	selector: 'app-equivalencias',
	templateUrl: './equivalencias.component.html',
	styleUrls: ['../estilo-abm.component.css']
})
export class EquivalenciasComponent implements OnInit {

	equivalencias: Equivalencia[];

	constructor(
		private restService: RestService,
		private utilesService: UtilesService,
		private dialogosService: DialogosService
	) { }

	ngOnInit() {
		this.getEquivalencias();
	}

	getEquivalencias() {
		this.restService.getEquivalencias().subscribe(equivalencias => {
			this.equivalencias = equivalencias;
		},
			(err: HttpErrorResponse) => {
				this.utilesService.mostrarMensajeDeError(err);
			});
	}

	abrirDialogoEquivalencia(equivalencia?: Equivalencia) {
		this.dialogosService
		.abrirDialogoEquivalencia(equivalencia)
		.subscribe(res => {
			this.getEquivalencias();
		});

	}
}