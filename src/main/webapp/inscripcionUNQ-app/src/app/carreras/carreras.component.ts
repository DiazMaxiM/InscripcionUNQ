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
  constructor(
    private restService: RestService,
    private utilesService: UtilesService
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
}
