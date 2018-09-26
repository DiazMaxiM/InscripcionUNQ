import { Component, OnInit} from '@angular/core';
import { RestService } from '../rest.service';
import { UtilesService } from '../utiles.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Materia } from '../materias-aprobadas/materia.model';

@Component({
  selector: 'app-materias',
  templateUrl: './materias.component.html',
  styleUrls: ['./materias.component.css']
})
export class MateriasComponent implements OnInit {
  materias: Materia[];

  constructor(
    private restService: RestService,
    private utilesService: UtilesService
  ) { }

  ngOnInit() {
    this.materias = JSON.parse(localStorage.getItem('materias'));
  }

  getMaterias() {
    this.restService.getMaterias().subscribe(materias => {
      this.materias = materias;
    },
    (err: HttpErrorResponse) => {
        this.utilesService.mostrarMensajeDeError(err);
    });
  }

  editarMateria(materia){
      console.log("Abrir dialogo");
  }



}
