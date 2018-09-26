import { Component, OnInit} from '@angular/core';
import { RestService } from '../rest.service';
import {UtilesService} from '../utiles.service';
import {HttpErrorResponse } from '@angular/common/http';
import { Carrera } from './carrera.model';

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
       console.log(confirma);
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

}
