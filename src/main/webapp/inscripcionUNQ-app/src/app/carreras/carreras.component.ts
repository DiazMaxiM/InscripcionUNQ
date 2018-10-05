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
  styleUrls: ['../estilo-abm.component.css']
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

  abrirDialogoParaLaCreacionDeCarrera() {
    const dialogRef = this.crearConfiguracionDialogoParaCarrera();
      dialogRef.afterClosed().subscribe( val => {
        if ( val != undefined) {
          this.crearNuevaCarrera(val);
        }
      });
  }

  crearNuevaCarrera(carrera: Carrera) {
    this.restService.agregarNuevaCarrera(carrera)
    .subscribe(res => {
     const mensaje = 'Se creó la nueva carrera con exito';
      this.utilesService.mostrarMensaje(mensaje);
      this.getCarreras();
    },
    (err: HttpErrorResponse) => {
      this.utilesService.mostrarMensajeDeError(err);
    });
  }

  abrirDialogoParaEdicionDeCarrera(carrera: Carrera) {
      const dialogRef = this.crearConfiguracionDialogoParaCarrera(carrera);
      dialogRef.afterClosed().subscribe( val => {
        if (val != undefined) {
          this.actualizarCarreraSeleccionada(val, carrera.id);
        }
      });
  }

  actualizarCarreraSeleccionada(carrera: Carrera, idCarrera) {
    carrera.id = idCarrera;
    this.actualizarCarrera(carrera);
  }

  actualizarCarrera(carrera) {
    this.restService.actualizarInformacionCarrera(carrera)
    .subscribe(res => {
     const mensaje = 'Los datos de la carrera actualizados con exito';
      this.utilesService.mostrarMensaje(mensaje);
      this.getCarreras();
    },
    (err: HttpErrorResponse) => {
      this.utilesService.mostrarMensajeDeError(err);
    });
  }

  crearConfiguracionDialogoParaCarrera(carrera?) {
    const dialogConfig = new  MatDialogConfig();

    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = false;
    dialogConfig.width = '600px';
    dialogConfig.height = '450px';
    dialogConfig.data = {
      carrera: carrera
    };

    const dialogRef = this.dialog.open(CarreraDialogoComponent,
            dialogConfig);

    return dialogRef;
  }

  cambiarEstado(carrera: Carrera, evento) {
    carrera.habilitada = evento.checked;
    this.actualizarCarrera(carrera);
  }
}
