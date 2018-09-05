import {Component, Inject} from '@angular/core';
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material';
import {DialogData} from './dialog-data.model' ;

 @Component({
     selector: 'app-custom-dialog-subject',
     templateUrl: './custom-dialog-subject.component.html'
 })
 export class CustomDialogSubjectComponent {
    subject: any;
    commissions: any
    selectedTimes: Date[];
    // hacer un mapa con los horarios de las comisiones
    // cuando se cambia de opcion llamar a la funcion que muestran los horarios
    // aceptar guardar el horario seleccionado
     constructor(
       public dialogRef: MatDialogRef<CustomDialogSubjectComponent>,
       @Inject(MAT_DIALOG_DATA) public data: DialogData) {
         this.subject = data.subject;
         this.commissions = this.subject.commissionsJson;
         console.log(this.commissions);
       }

     aceptar() {
         this.dialogRef.close();
     }
 }
