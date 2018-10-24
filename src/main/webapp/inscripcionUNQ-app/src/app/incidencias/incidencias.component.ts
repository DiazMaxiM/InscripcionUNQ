import { Component, OnInit } from '@angular/core';
import { RestService } from '../rest.service';
import { UtilesService } from '../utiles.service';
import { MatDialog, MatDialogConfig } from '@angular/material';
import { HttpErrorResponse } from '@angular/common/http';
import { IncidenciaEstado } from './incidencia-estado.model';
import { ModificacionDeIncidenciaDialogoComponent } from '../modificacion-de-incidencia-dialogo/modificacion-de-incidencia-dialogo.component'

@Component({
  selector: 'app-incidencias',
  templateUrl: './incidencias.component.html',
  styleUrls: ['../estilo-abm.component.css']
})
export class IncidenciasComponent implements OnInit {
  incidencias: IncidenciaEstado[];
  idIncidencia;

  constructor(
    private restService: RestService,
    private utilesService: UtilesService,
    private dialog: MatDialog
  ) {}

  ngOnInit(){
    this.incidencias = JSON.parse(localStorage.getItem('incidencias'));
  }

  getIncidencias() {
    this.restService.getIncidencias().subscribe(incidencias => {
      this.incidencias = JSON.parse(JSON.stringify(incidencias));
    },
    (err) => {
      this.utilesService.mostrarMensajeDeError(err);
    });
  }

  actualizarIncidenciaSeleccionada(incidencia: IncidenciaEstado, idIncidencia) {
    incidencia.id = idIncidencia;
    this.actualizarIncidencia(incidencia);
  }

  actualizarIncidencia(incidencia) {
    this.restService.actualizarIncidencia(incidencia)
      .subscribe(res => {
        const mensaje = 'Los datos de la incidencia fueron actualizados con éxito';
        this.utilesService.mostrarMensaje(mensaje);
        this.getIncidencias(); 
      },
        (err: HttpErrorResponse) => {
          this.utilesService.mostrarMensajeDeError(err);
        });
        
  }

  cambiarEstado(tipoEstadoIncidencia,incidencia: IncidenciaEstado) {
    incidencia.tipoEstadoIncidencia = tipoEstadoIncidencia;
    this.actualizarIncidencia(incidencia);
  }

  abrirDialogoParaEdicionDeIncidencia(incidencia: IncidenciaEstado){
    const dialogRef = this.crearConfiguracionDialogoParaIncidencia(incidencia);
    dialogRef.afterClosed().subscribe( val => {
      if (val != undefined) {
        this.cambiarEstado(val,incidencia);
      }
    });
  }

  crearConfiguracionDialogoParaIncidencia(incidencia?) {
    const dialogConfig = new  MatDialogConfig();

    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = false;
    dialogConfig.width = '600px';
    dialogConfig.height = '450px';
    dialogConfig.data = {
      incidencia: incidencia
    };

    const dialogRef = this.dialog.open(ModificacionDeIncidenciaDialogoComponent,
            dialogConfig);

    return dialogRef;
  }
  
}