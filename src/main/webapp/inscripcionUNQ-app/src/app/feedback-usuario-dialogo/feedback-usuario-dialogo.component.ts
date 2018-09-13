import {Component, Inject} from '@angular/core';
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material';
import {DataDialogo} from './data-dialogo.model' ;

@Component({
    selector: 'app-feedback-usuario-dialogo',
    templateUrl: './feedback-usuario-dialogo.component.html',
    styleUrls: ['./feedback-usuario-dialogo.component.css']
})
export class FeedbackUsuarioDialogoComponent {

   mensaje: string;

    constructor(
      public dialogRef: MatDialogRef<FeedbackUsuarioDialogoComponent>,
      @Inject(MAT_DIALOG_DATA) public data: DataDialogo) {
        this.mensaje = data.mensaje;
      }

    aceptar() {
        this.dialogRef.close();
    }
}
