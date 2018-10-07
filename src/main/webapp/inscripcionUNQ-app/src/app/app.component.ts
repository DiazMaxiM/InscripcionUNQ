import { Component } from '@angular/core';
import { RestService } from './rest.service';
import { UtilesService } from './utiles.service';
import {HttpErrorResponse } from '@angular/common/http';
import { Incidencia } from './incidencia-dialogo/incidencia.model';
import { IncidenciaDialogoComponent } from './incidencia-dialogo/incidencia-dialogo.component';
import { MatDialog, MatDialogConfig } from '@angular/material';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent{
  title = 'Inscripción UNQ';
  incidencia: Incidencia;

  constructor(
    private restService: RestService,
    private utilesService: UtilesService,
    private dialog: MatDialog
) { }

  abrirDialogoParaLaCreacionDeIncidencia() {
    const dialogRef = this.crearConfiguracionDialogoParaIncidencia();
      dialogRef.afterClosed().subscribe( val => {
        if ( val != undefined) {
          this.crearNuevaIncidencia(val);
        }
      });
  }

  crearConfiguracionDialogoParaIncidencia() {
    const dialogConfig = new  MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = false;
    dialogConfig.width = '600px';
    dialogConfig.height = '450px';
    dialogConfig.data = {
      incidencia: null
    };

    const dialogRef = this.dialog.open(IncidenciaDialogoComponent,
            dialogConfig);

    return dialogRef;
  }

  crearNuevaIncidencia(incidencia: Incidencia) {
    this.restService.agregarIncidencia(incidencia)
    .subscribe(res => {
      const mensaje = 'Se envió con éxito la incidencia';
      this.utilesService.mostrarMensaje(mensaje);
    },
    (err: HttpErrorResponse) => {
      this.utilesService.mostrarMensajeDeError(err);
    });
  }
}
