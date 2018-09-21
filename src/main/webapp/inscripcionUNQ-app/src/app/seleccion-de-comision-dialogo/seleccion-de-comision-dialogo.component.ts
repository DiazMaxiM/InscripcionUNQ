import {Component, Inject} from '@angular/core';
import {MatDialogRef, MAT_DIALOG_DATA} from '@angular/material';
import {DataDialogo} from './data-dialogo.model' ;
import { ComisionSeleccionada } from './comision-seleccionada.model';
import { FormBuilder} from '@angular/forms';
import {RegistroDeComisionesSeleccionadasService} from '../seleccion-de-materias-por-cursar/registro-de-comisiones-seleccionadas.service';
@Component({
     selector: 'app-seleccion-de-comision-dialogo',
     templateUrl: './seleccion-de-comision-dialogo.component.html',
     styleUrls: ['./seleccion-de-comision-dialogo.component.css']
 })
 export class SeleccionDeComisionDialogoComponent {
    materia: any;
    comisiones: any[];
    comisionActual: any;

    constructor(
      private registroComisionesService: RegistroDeComisionesSeleccionadasService,
      public dialogRef: MatDialogRef<SeleccionDeComisionDialogoComponent>,
      private fb: FormBuilder,
       @Inject(MAT_DIALOG_DATA) public data: DataDialogo) {
         this.materia = data.materia;
         this.comisiones = this.materia.commissionsJson;
 }

 enviarRegistro(comisionSeleccionada: ComisionSeleccionada) {
       this.dialogRef.close(comisionSeleccionada);
  }

 close() {
       this.dialogRef.close();
  }

  comisionSeleccionada(idComision) {
    const comision = this.comisiones.find(c => c.id == idComision);
    const registro = this.registroComisionesService.crearRegistroDeComisionSeleccionada(this.materia.id,comision);
    this.enviarRegistro(registro);
  }
 }
