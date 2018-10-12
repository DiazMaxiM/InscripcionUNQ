import { Component, OnInit } from '@angular/core';
import { UtilesService } from '../utiles.service';
import { RestService } from '../rest.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Materia } from '../materias/materia.model';
import { MatDialogRef } from '@angular/material';
import { Equivalencia } from '../equivalencias/equivalencia.model';

@Component({
    selector: 'app-equivalencia-dialogo',
    templateUrl: './equivalencia-dialogo.component.html',
    styleUrls: ['../dialogo-abm.component.css']
})

export class EquivalenciaDialogoComponent implements OnInit {
  materias: Materia[];
  materiasSeleccionadas: Materia[] = [];
  materiaBuscada;

  constructor(
    private restService: RestService,
    private utilesService: UtilesService,
    private dialogRef: MatDialogRef<EquivalenciaDialogoComponent>)
    {}

  ngOnInit(){
    this.getMaterias();
  }

  getMaterias(){
    this.restService.getMaterias().subscribe(materias => {
      this.materias = materias;
    },
      (err: HttpErrorResponse) => {
        this.utilesService.mostrarMensajeDeError(err);
      });
  }

  onChange(materia, $event) {
    if ($event.checked) {
      this.materiasSeleccionadas.push(materia);
    }
    else {
        this.materiasSeleccionadas.forEach(materiaSeleccionada => {
            if (materia.id == materiaSeleccionada.id) {
                this.materiasSeleccionadas.splice(this.materiasSeleccionadas.indexOf(materiaSeleccionada), 1);
            }
        });
    }
  }

  guardar() {
    if(this.materiasSeleccionadas.length != 2){
      this.utilesService.mostrarMensaje('Debe seleccionar dos materias para poder crear una equivalencia');
    } else {
      const equivalencia = new Equivalencia(this.materiasSeleccionadas[0], this.materiasSeleccionadas[1]);
      this.dialogRef.close(equivalencia);
    }
  }

  cerrar() {
    this.dialogRef.close();
  }
 }