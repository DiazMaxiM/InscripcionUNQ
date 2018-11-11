import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { DataDialogo } from './data-dialogo.model';

@Component({
	selector: 'app-feedback-usuario-dialogo',
	templateUrl: './feedback-usuario-dialogo.component.html',
	styleUrls: ['./feedback-usuario-dialogo.component.css', '../app.component.css']
})
export class FeedbackUsuarioDialogoComponent {

	mensaje: string;
	cargando: boolean;
	confirmar: boolean;

	constructor(
		public dialogRef: MatDialogRef<FeedbackUsuarioDialogoComponent>,
		@Inject(MAT_DIALOG_DATA) public data: DataDialogo) {
		this.mensaje = data.mensaje;
		this.cargando = data.cargando;
		this.confirmar = data.confirmar;
	}

	aceptar() {
		this.dialogRef.close();
	}

	cancelarAccion() {
		this.dialogRef.close(false);
	}

	confirmarAccion() {
		this.dialogRef.close(true);
	}
}