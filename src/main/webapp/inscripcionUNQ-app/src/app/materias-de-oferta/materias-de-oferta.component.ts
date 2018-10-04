import { Component, OnInit } from '@angular/core';
import { RestService } from '../rest.service';
import { UtilesService } from '../utiles.service';
import { Materia } from '../materias/materia.model';

@Component({
  selector: 'app-materias-de-oferta',
  templateUrl: './materias-de-oferta.component.html'
})
export class MateriasDeOfertaComponent implements OnInit {
  materias: Materia[];
  idMateria;

  constructor(
    private restService: RestService,
    private utilesService: UtilesService
  ) { }

  ngOnInit() {
    this.materias = JSON.parse(localStorage.getItem('materias-de-oferta'));  
  }

  abrirDialogoParaAgregarComision(materia) {
    const dialogRef = this.crearConfiguracionDialogoParaComision();
  }

  crearNuevaComision(materia: Materia) { 
  }

  abrirDialogoParaVerComisiones(){
  }


  crearConfiguracionDialogoParaComision(comision?) {
  }

}