import { Component, OnInit} from '@angular/core';
import {UtilesService} from '../utiles.service';

@Component({
  selector: 'app-encuestas',
  templateUrl: './encuestas.component.html',
	styleUrls: ['../estilo-abm.component.css']
})

export class EncuestasComponent implements OnInit {
  constructor(
  private utilesService: UtilesService
) { }
  encuestas: any[];

  ngOnInit() {
      this.encuestas = JSON.parse(localStorage.getItem('encuestas'));
  }

  editarEncuesta(idEncuestaActual) {
      localStorage.setItem('idEncuestaActual', idEncuestaActual);
      this.utilesService.irA('verificacion-de-datos');
  }
}