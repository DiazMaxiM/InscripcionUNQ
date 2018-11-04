import { Component, OnInit} from '@angular/core';
import {UtilesService} from '../utiles.service';
import { EncuestaDialogoComponent } from '../encuesta-dialogo/encuesta-dialogo.component';
import { MatDialog, MatDialogConfig } from '@angular/material';
import { RestService } from '../rest.service';
import { Encuesta } from '../encuesta-dialogo/encuesta.model';
import { OfertasDeEncuestaDialogoComponent } from '../ofertas-de-encuesta-dialogo/ofertas-de-encuesta-dialogo.component';

@Component({
  selector: 'app-encuestas',
  templateUrl: './encuestas.component.html',
	styleUrls: ['../estilo-abm.component.css']
})

export class EncuestasComponent implements OnInit {
	
  constructor(
	private restService: RestService,
	private utilesService: UtilesService,
	private dialog: MatDialog
) { }
  encuestas: any[];

  ngOnInit() {
      this.encuestas = JSON.parse(localStorage.getItem('encuestas'));
  }

	
	abrirDialogoParaAltaOModificacionEncuesta(encuesta){
		const dialogRef = this.crearConfiguracionDialogoParaEncuesta(encuesta);
		dialogRef.afterClosed().subscribe(val => {
			this.getEncuestas();
		});
	}

	getEncuestas() {
		this.restService.getEncuestas().subscribe(encuestas => {
			this.encuestas = encuestas;
		},
			(err) => {
				this.utilesService.mostrarMensajeDeError(err);
			});
	}

	crearConfiguracionDialogoParaEncuesta(encuesta?) {
		const dialogConfig = new MatDialogConfig();
		dialogConfig.disableClose = true;
		dialogConfig.autoFocus = false;
		dialogConfig.width = 'auto';
		dialogConfig.height = 'auto';
		dialogConfig.data = {
			encuesta: encuesta
		};
		const dialogRef = this.dialog.open(EncuestaDialogoComponent,
			dialogConfig);
		return dialogRef;
	}

	crearConfiguracionDialogoParaOfertas(encuesta?) {
		const dialogConfig = new MatDialogConfig();
		dialogConfig.disableClose = true;
		dialogConfig.autoFocus = false;
		dialogConfig.width = 'auto';
		dialogConfig.height = 'auto';
		dialogConfig.data = {
			encuesta: encuesta
		};
		const dialogRef = this.dialog.open(OfertasDeEncuestaDialogoComponent,
			dialogConfig);
		return dialogRef;
	}

	abrirDialogoDeOfertasAcademicas(encuesta: Encuesta) {
		const dialogRef = this.crearConfiguracionDialogoParaOfertas(encuesta);
		dialogRef.afterClosed().subscribe(val => {
			this.getEncuestas();
		});
	}
}