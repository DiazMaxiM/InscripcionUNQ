import { Component, OnInit } from "@angular/core";
import { RestService } from "../rest.service";
import { UtilesService } from "../utiles.service";
import { Periodo } from "./periodo.model";
import { DialogosService } from '../dialogos.service';

@Component({
  selector: "app-periodo",
  templateUrl: "./periodo.component.html",
  styleUrls: ["../estilo-abm.component.css"]
})
export class PeriodoComponent implements OnInit {
  mostrarPeriodos;

  periodos: Periodo[];
  constructor(
    private restService: RestService,
    private utilesService: UtilesService,
		private dialogosService: DialogosService
		) {}

  ngOnInit() {
    this.periodos = JSON.parse(localStorage.getItem("periodos"));
    this.hayPeriodosParaMostrar();
  }

  hayPeriodosParaMostrar() {
    this.mostrarPeriodos = this.periodos.length > 0;
  }


  abrirDialogoParaCreacionDePeriodo() {
		this.dialogosService
		.abrirDialogoPeriodo()
		.subscribe(val => {
			this.getPeriodos();
		});
  }

  getPeriodos() {
    this.restService.getPeriodos().subscribe(
      periodos => {
        this.periodos = periodos;
        this.hayPeriodosParaMostrar();
      },
      err => {
        this.utilesService.mostrarMensajeDeError(err);
      }
    );
  }
}