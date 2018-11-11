import { Component, OnInit } from "@angular/core";
import { RestService } from "../rest.service";
import { UtilesService } from "../utiles.service";
import { HttpErrorResponse } from "@angular/common/http";
import { MatDialog, MatDialogConfig } from "@angular/material";
import { OfertaAcademica } from "./oferta-academica.model";
import { OfertaAcademicaDialogoComponent } from "../oferta-academica-dialogo/oferta-academica.dialogo.component";
import { SeleccionDePeriodoDialogoComponent } from "../seleccion-de-periodo-dialogo/seleccion-de-periodo-dialogo.component";
import { Periodo } from "../periodos/periodo.model";

@Component({
	selector: "app-oferta-academica",
	templateUrl: "./oferta-academica.component.html",
	styleUrls: ["../estilo-abm.component.css"]
})
export class OfertaAcademicaComponent implements OnInit {
	ofertas: OfertaAcademica[];

	constructor(
		private restService: RestService,
		private utilesService: UtilesService,
		private dialog: MatDialog
	) { }

	ngOnInit() {
		this.ofertas = JSON.parse(localStorage.getItem("ofertas"));
	}

	getOfertas() {
		this.restService.getOfertas().subscribe(
			ofertas => {
				this.ofertas = ofertas;
			},
			(err: HttpErrorResponse) => {
				this.utilesService.mostrarMensajeDeError(err);
			}
		);
	}

	abrirDialogoParaCreacionDeOferta() {
		const dialogRef = this.crearConfiguracionDialogoParaOferta();
		dialogRef.afterClosed().subscribe(val => {
			this.getOfertas();
		});
	}

	crearConfiguracionDialogoParaOferta(oferta?) {
		const dialogConfig = new MatDialogConfig();

		dialogConfig.disableClose = true;
		dialogConfig.autoFocus = false;
		dialogConfig.width = "600px";
		dialogConfig.height = "600px";
		dialogConfig.data = {
			oferta: oferta
		};

		const dialogRef = this.dialog.open(
			OfertaAcademicaDialogoComponent,
			dialogConfig
		);

		return dialogRef;
	}

	abrirDialogoParaEdicionDeOferta(ofertaSeleccionada) {
		const dialogRef = this.crearConfiguracionDialogoParaOferta(
			ofertaSeleccionada
		);
		dialogRef.afterClosed().subscribe(val => {
			this.getOfertas();
		});
	}

	actualizarOfertaSeleccionada(oferta: OfertaAcademica, ofertaSeleccionada) {
		oferta.id = ofertaSeleccionada.id;
		oferta.nroComisionesCreadas = ofertaSeleccionada.nroComisionesCreadas;
		this.actualizarOferta(oferta);
	}

	actualizarOferta(oferta) {
		this.restService.actualizarInformacionDeOferta(oferta).subscribe(
			res => {
				const mensaje =
					"Los datos de la oferta académica fueron actualizados con exito";
				this.utilesService.mostrarMensaje(mensaje);
				this.getOfertas();
			},
			(err: HttpErrorResponse) => {
				this.utilesService.mostrarMensajeDeError(err);
			}
		);
	}

	cambiarEstado(oferta, evento) {
		oferta.habilitada = evento.checked;
		this.actualizarOferta(oferta);
	}

	irAComisiones(oferta: OfertaAcademica) {
		this.restService.getComisionesDeOferta(oferta.id).subscribe(
			comisiones => {
				localStorage.setItem(
					"comisiones-de-oferta",
					JSON.stringify(comisiones)
				);
				localStorage.setItem("oferta-seleccionada", JSON.stringify(oferta));
				this.utilesService.irA("comisiones-de-oferta");
			},
			err => {
				this.utilesService.mostrarMensajeDeError(err);
			}
		);
	}

	crearConfiguracionDialogoParaSeleccionDePeriodo() {
		const dialogConfig = new MatDialogConfig();

		dialogConfig.disableClose = true;
		dialogConfig.autoFocus = false;
		dialogConfig.width = "400px";
		dialogConfig.height = "200px";

		const dialogRef = this.dialog.open(
			SeleccionDePeriodoDialogoComponent,
			dialogConfig
		);
		return dialogRef;
	}

	abrirDialogoParaSeleccionDePeriodo(idOferta) {
		const dialogRef = this.crearConfiguracionDialogoParaSeleccionDePeriodo();
		dialogRef.afterClosed().subscribe(idPeriodo => {
			if (idPeriodo != null) {
				this.clonarOferta(idPeriodo, idOferta);
			}
		});
	}

	clonarOferta(idPeriodo, idOferta) {
		const oferta = new OfertaAcademica();
		oferta.id = idOferta;
		const periodo = new Periodo();
		periodo.id = idPeriodo;
		oferta.periodo = periodo;
		this.restService.clonarOferta(oferta).subscribe(
			res => {
				this.utilesService.mostrarMensaje('La oferta acádemica fue clonada con éxito');
				this.getOfertas();
			},
			(err: HttpErrorResponse) => {
				this.utilesService.mostrarMensajeDeError(err);
			}
		);
	}
}