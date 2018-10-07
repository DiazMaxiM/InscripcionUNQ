import { Component, OnInit} from '@angular/core';
import { RestService } from '../rest.service';
import {UtilesService} from '../utiles.service';
import { Periodo } from './periodo.model';
import { MatDialogConfig, MatDialog } from '@angular/material';
import { OfertaAcademica } from '../oferta-academica/oferta-academica.model';
import { PeriodoDialogoComponent } from '../periodo-dialogo/periodo-dialogo.component';


@Component({
  selector: 'app-periodo',
  templateUrl: './periodo.component.html',
  styleUrls: ['../estilo-abm.component.css']
})
export class PeriodoComponent implements OnInit{

   periodos: Periodo[];
    constructor(
      private restService: RestService,
      private utilesService: UtilesService,
      private dialog: MatDialog
    ) {}

    ngOnInit() {
      this.periodos = JSON.parse(localStorage.getItem('periodos'));
    }

    crearConfiguracionDialogoParaPeriodo() {
      const dialogConfig = new  MatDialogConfig();
      dialogConfig.disableClose = true;
      dialogConfig.autoFocus = false;
      dialogConfig.width = '600px';
      dialogConfig.height = '450px';
      const dialogRef = this.dialog.open(PeriodoDialogoComponent, dialogConfig);
      return dialogRef;
    }

    abrirDialogoParaCreacionDePeriodo(){
      const dialogRef = this.crearConfiguracionDialogoParaPeriodo();
      dialogRef.afterClosed().subscribe( val => {
        if ( val != undefined) {
          this.crearNuevoPeriodo(val);
        }
      });
    }

    crearNuevoPeriodo(periodo: Periodo) {

    }
}
