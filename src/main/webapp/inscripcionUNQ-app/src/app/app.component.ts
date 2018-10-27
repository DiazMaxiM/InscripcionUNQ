import { Component, OnInit, AfterViewInit } from '@angular/core';
import { RestService } from './rest.service';
import { UtilesService } from './utiles.service';
import {HttpErrorResponse } from '@angular/common/http';
import { Incidencia } from './incidencia-dialogo/incidencia.model';
import { IncidenciaDialogoComponent } from './incidencia-dialogo/incidencia-dialogo.component';
import { MatDialog, MatDialogConfig } from '@angular/material';
import { usuarioLogueadoService } from './usuario-logueado.service';
import { EdicionUsuarioDialogoComponent } from './edicion-usuario-dialogo/edicion-usuario-dialogo.component';
import { AppMensajes } from './app-mensajes.model';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit, AfterViewInit{
  title = 'Inscripción UNQ';
  incidencia: Incidencia;
  hayUsuarioLogueado:boolean;

  constructor(
    private restService: RestService,
    private utilesService: UtilesService,
    private dialog: MatDialog,
    private usuarioLogueado: usuarioLogueadoService
) { }

ngOnInit() {
  this.usuarioLogueado.hayUsuarioLogueaado.subscribe(res => {
    this.hayUsuarioLogueado = res;
  });
}

ngAfterViewInit() {
  this.usuarioLogueado.hayUsuarioLogueaado.subscribe(res => {
    this.hayUsuarioLogueado = res;
  });
}

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

  abrirDialogoParaModificacionDePassword() {
    const dialogConfig = new  MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = false;
    dialogConfig.width = '600px';
    dialogConfig.height = '300px';
    const dialogRef = this.dialog.open(EdicionUsuarioDialogoComponent,
            dialogConfig);
    dialogRef.afterClosed().subscribe(res => {
      if (AppMensajes.OK == res) {
          this.utilesService.mostrarMensaje(AppMensajes.MODIFICACION_PASSWORD_EXITOSO);
      }
  });}

}
