import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import { FormBuilder, Validators, FormGroup, FormControl } from '@angular/forms';
import { UtilesService } from '../utiles.service';

import { RestService } from '../rest.service';


@Component({
	selector: 'app-encuesta-dialogo',
	templateUrl: './encuesta-dialogo.component.html',
	styleUrls: ['../dialogo-abm.component.css']
})

export class EncuestaDialogoComponent implements OnInit {

	constructor(
		private fb: FormBuilder,
		private utilesService: UtilesService,
		private dialogRef: MatDialogRef<EncuestaDialogoComponent>,
		private restService: RestService) {}

	ngOnInit() {
	}

}