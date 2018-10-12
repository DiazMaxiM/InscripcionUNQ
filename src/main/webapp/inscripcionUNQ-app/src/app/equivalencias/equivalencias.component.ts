import { Component, OnInit } from '@angular/core';
import { RestService } from '../rest.service';
import { UtilesService } from '../utiles.service';
import { HttpErrorResponse } from '@angular/common/http';
import { MatDialog, MatDialogConfig } from '@angular/material';
import { Equivalencia } from './equivalencia.model';
import { EquivalenciaDialogoComponent } from '../equivalencia-dialogo/equivalencia-dialogo.component'

@Component({
  selector: 'app-equivalencias',
  templateUrl: './equivalencias.component.html',
  styleUrls: ['../estilo-abm.component.css']
})
export class EquivalenciasComponent implements OnInit {

  equivalencias: Equivalencia[];
  equivalenciaActual: Equivalencia;

  constructor(
    private restService: RestService,
    private utilesService: UtilesService,
    private dialog: MatDialog
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
    this.equivalenciaActual = equivalencia;
    const dialogRef = this.crearConfiguracionDialogoParaEquivalencia(equivalencia);
    dialogRef.afterClosed().subscribe( val => {
      if ( val != undefined) {
        this.guardarEquivalencia(val);
      }
    });
  }

  guardarEquivalencia(equivalencia) {
    if (this.equivalenciaActual == null){
      this.crearNuevaEquivalencia(equivalencia);
    } else{
      this.actualizarEquivalencia(equivalencia);
    }

  }
  actualizarEquivalencia(equivalencia: Equivalencia) {
    equivalencia.id = this.equivalenciaActual.id;
    this.equivalenciaActual = null;
    this.restService.actualizarEquivalencia(equivalencia)
    .subscribe(res => {
      const mensaje = 'La equivalencia fue actualizada con éxito';
       this.utilesService.mostrarMensaje(mensaje);
       this.getEquivalencias();
     },
     (err: HttpErrorResponse) => {
       this.utilesService.mostrarMensajeDeError(err);
     });
  }

  crearConfiguracionDialogoParaEquivalencia(equivalencia?) {
    const dialogConfig = new  MatDialogConfig();

    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = false;
    dialogConfig.width = '450px';
    dialogConfig.height = '250px';
    dialogConfig.data = {
      equivalencia: equivalencia
    };

    const dialogRef = this.dialog.open(EquivalenciaDialogoComponent,
            dialogConfig);

    return dialogRef;
  }

  crearNuevaEquivalencia(equivalencia: Equivalencia) {
    this.restService.agregarNuevaEquivalencia(equivalencia)
    .subscribe(res => {
     const mensaje = 'Se creó la nueva equivalencia con éxito';
      this.utilesService.mostrarMensaje(mensaje);
      this.getEquivalencias();
    },
    (err: HttpErrorResponse) => {
      this.utilesService.mostrarMensajeDeError(err);
    });
  }
}
