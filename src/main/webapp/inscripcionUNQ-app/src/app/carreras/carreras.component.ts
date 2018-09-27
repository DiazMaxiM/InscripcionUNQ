import { Component, OnInit} from '@angular/core';
import { RestService } from '../rest.service';
import {UtilesService} from '../utiles.service';
import {HttpErrorResponse } from '@angular/common/http';
import { Carrera } from './carrera.model';
import { CarreraDialogoComponent } from '../carrera-dialogo/carrera-dialogo.component';
import { MatDialog, MatDialogConfig } from '@angular/material';

@Component({
  selector: 'app-carreras',
  templateUrl: './carreras.component.html',
  styleUrls: ['./carreras.component.css']
})
export class CarrerasComponent implements OnInit {

  carreras: Carrera[];
  idCarrera;

  constructor(
    private restService: RestService,
    private utilesService: UtilesService,
    private dialog: MatDialog
) { }

  ngOnInit() {
    this.carreras = JSON.parse(localStorage.getItem('carreras'));
  }

  getCarreras() {
    this.restService.getCarreras().subscribe(carreras => {
      this.carreras = carreras;
    },
    (err: HttpErrorResponse) => {
        this.utilesService.mostrarMensajeDeError(err);
    });
  }

  eliminarCarrera(idCarrera) {
     const mensaje = '¿Está seguro de que desea eliminar la carrera seleccionada?';
     this.utilesService.mostrarDialogoConfirmacion(mensaje).subscribe(confirma => {
       if (confirma) {
        this.eliminar(idCarrera);
       }
    });
  }

  eliminar(idCarrera) {
    this.restService.eliminarCarrera(idCarrera).subscribe(res => {
      this.utilesService.mostrarMensaje('Se ha eliminado la carrera');
      this.getCarreras();
    },
    (err: HttpErrorResponse) => {
        this.utilesService.mostrarMensajeDeError(err);
    });
  }

  editarCarrera( carrera: Carrera) {

      const dialogConfig = new MatDialogConfig();

      dialogConfig.disableClose = true;
      dialogConfig.autoFocus = false;
      dialogConfig.width = '600px';
      dialogConfig.height = '450px';
      dialogConfig.data = {
        carrera: carrera
      };

      const dialogRef = this.dialog.open(CarreraDialogoComponent,
          dialogConfig);


      dialogRef.afterClosed().subscribe( val => {this.actualizarCarrera(val)}
      );
  }

  actualizarCarrera(carrera: Carrera) {

  }
}
