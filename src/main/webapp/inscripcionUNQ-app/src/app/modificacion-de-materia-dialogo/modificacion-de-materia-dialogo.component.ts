import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { FormBuilder } from '@angular/forms';
import { DataDialogo } from '../seleccion-de-comision-dialogo/data-dialogo.model' ;

@Component({
     selector: 'app-modificacion-de-materia-dialogo',
     templateUrl: './modificacion-de-materia-dialogo.component.html',
     styleUrls: ['./modificacion-de-materia-dialogo.component.css']
 })
 export class ModificacionDeMateriaDialogoComponent {
    materia: any;

    constructor(
      public dialogRef: MatDialogRef<ModificacionDeMateriaDialogoComponent>,
      private fb: FormBuilder,
       @Inject(MAT_DIALOG_DATA) public data: DataDialogo) {
         this.materia = data.materia;
      }

    close() {
         this.dialogRef.close();
    }
}
