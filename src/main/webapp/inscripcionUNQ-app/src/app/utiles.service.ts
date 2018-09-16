import { Injectable } from '@angular/core';
import { MatDialog, MatDialogConfig} from '@angular/material';
import {FeedbackUsuarioDialogoComponent} from './feedback-usuario-dialogo/feedback-usuario-dialogo.component';
import { Router } from '@angular/router';

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

}
