import { Component, OnInit, ViewChild} from '@angular/core';
import { RestService } from '../rest.service';
import {PageEvent} from '@angular/material';
import { Materia } from './materia.model';
import {UtilesService} from '../utiles.service';

@Component({
  selector: 'app-materias-aprobadas',
  templateUrl: './materias-aprobadas.component.html',
  styleUrls: ['./materias-aprobadas.component.css']
})
export class MateriasAprobadasComponent implements OnInit {

  materias: Materia[] = [];
  materiasEnPagina = [];
  idEstudiante: string;

  // MatPaginator Inputs
 length = 0;
 pageSize = 10;

 // MatPaginator Output
 pageEvent: PageEvent;

  constructor(
    private restService: RestService,
    private utilesService: UtilesService
  ) {}

  ngOnInit() {
        this.idEstudiante = localStorage.getItem('idEstudiante');
        this.getMateriasAprobadas();
    }

  getMateriasAprobadas() {
    this.restService.getMateriasAprobadas(this.idEstudiante)
    .subscribe(subjects => {
      this.materias = subjects;
      this.length = this.materias.length;
      this.materiasEnPagina = this.materias.slice(0, this.pageSize);
    }, (err) => {
      this.utilesService.mostrarMensajeDeError(err);
   });
  }

    actualizarMateriasAprobadas() {
      this.utilesService.activarDialogoCargando();
      this.restService.actualizarMateriasAprobadas(this.idEstudiante, this.materias)
      .subscribe(res => {
        this.utilesService.desactivarDialogoCargandoYRedireccionar('materias-por-cursar');
      }, (err) => {
        this.utilesService.mostrarMensajeDeError(err);
     });
    }

    seleccionarMateria(id) {
      const result = [];
      for (const i in this.materias) {
        if (this.materias[i].id == id) {
          result.push({
            'id': this.materias[i].id,
            'codigo': this.materias[i].codigo,
            'nombre': this.materias[i].nombre,
            'aprobada': !this.materias[i].aprobada
          });
        } else {
          result.push(this.materias[i]);
        }
      }
      this.materias = result;
    }

  cambiarPagina(e) {
    const firstCut = e.pageIndex * e.pageSize;
    const secondCut = firstCut + e.pageSize;
    this.materiasEnPagina = this.materias.slice(firstCut, secondCut);
}

}
