import { Injectable } from '@angular/core';
import { MatDialog, MatDialogConfig} from '@angular/material';
import {FeedbackUsuarioDialogoComponent} from './feedback-usuario-dialogo/feedback-usuario-dialogo.component';
import { Router } from '@angular/router';
import {HttpErrorResponse } from '@angular/common/http';

@Injectable()
export class UtilesService {
  constructor(
    private dialog: MatDialog,
    private router: Router,
  ) {}

  mostrarMensajeYRedireccionar(mensaje, ruta) {
    const dialogConfig = this.crearConfiguracionDelDialogo(mensaje);
    const dialogRef = this.dialog.open(FeedbackUsuarioDialogoComponent,
      dialogConfig);

    dialogRef.afterClosed().subscribe(result => {
        this.router.navigate([ruta]);
      });

  }

  crearConfiguracionDelDialogo(msj: String) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.data = {
        mensaje: msj
      };
    return dialogConfig;
  }

 irA(ruta) {
   this.router.navigate([ruta]);
 }

 mostrarMensaje(mensaje) {
   const dialogConfig = this.crearConfiguracionDelDialogo(mensaje);
   const dialogRef = this.dialog.open(FeedbackUsuarioDialogoComponent,
     dialogConfig);
 }

 salir() {
   this.irA('');
 }

 crearFecha(fecha) {
    const date = new Date();
    date.setDate(fecha.dayOfMonth);
    date.setMonth(fecha.monthValue -1 );
    date.setFullYear(fecha.year);
    date.setHours(fecha.hour);
    date.setMinutes(fecha.minute);
    date.setSeconds(fecha.second);
    return date;
  }

  mostrarMensajeYSalir(mensaje) {
    this.mostrarMensaje(mensaje);
    this.salir();

  }

  mostrarMensajeDeError(error: HttpErrorResponse) {
     console.log(error);
     let mensaje;
     if (error.status >= 500) {
       mensaje = 'En este momento no realizar la petición. Por favor uelve a intentarlo';
     } else {
       mensaje = error.error.msg;
     }
     this.mostrarMensaje(mensaje);
  }

}
