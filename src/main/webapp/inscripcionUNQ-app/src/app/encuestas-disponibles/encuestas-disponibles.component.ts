import { Component, OnInit} from '@angular/core';
import {UtilesService} from '../utiles.service';

@Component({
  selector: 'app-encuestas-disponibles',
  templateUrl: './encuestas-disponibles.component.html',
  styleUrls: ['./encuestas-disponibles.component.css']
})
export class EncuestasDisponiblesComponent implements OnInit {
  constructor(
  private utilesService: UtilesService
) { }
  encuestas: any[];

  ngOnInit() {
      this.encuestas = JSON.parse(localStorage.getItem('encuestasVigentes'));
      if (this.encuestas.length == 0) {
        const mensaje = 'No se encontraron encuestas para el DNI ingresado';
        this.utilesService.mostrarMensajeYSalir(mensaje);
      }
  }

  editarEncuesta(idEncuestaActual) {
      localStorage.setItem('idEncuestaActual', idEncuestaActual);
      this.utilesService.irA('verificacion-de-datos');
  }
}
