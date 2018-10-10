import { Component, OnInit } from '@angular/core';
import { RestService } from '../rest.service';
import { UtilesService } from '../utiles.service';
import { Comision } from './comision.model';
import { MatDialogConfig, MatDialog } from '@angular/material';
import { HttpErrorResponse } from '@angular/common/http';
import { OfertaAcademica } from '../oferta-academica/oferta-academica.model';

@Component({
  selector: 'app-comisiones-de-oferta',
  templateUrl: './comisiones-de-oferta.component.html',
  styleUrls: ['../estilo-abm.component.css']
})

export class ComisionesDeOfertaComponent implements OnInit {
  comisiones: Comision[];
  idOferta;
  oferta: OfertaAcademica;
  comisionSeleccionada;
  comisionBuscada;

  constructor(
    private restService: RestService,
    private utilesService: UtilesService,
    private dialog: MatDialog
  ) { }

  ngOnInit() {
    this.comisiones = this.ordenarComisionesPorNombre(JSON.parse(localStorage.getItem('comisiones-de-oferta')));
    this.oferta = JSON.parse(localStorage.getItem('oferta-seleccionada'));
  }

  getComisiones(id) {
    this.restService.getComisionesDeOferta(id).subscribe(comisiones => {
      
      this.comisiones = this.ordenarComisionesPorNombre(comisiones);
    },
      (err: HttpErrorResponse) => {
        this.utilesService.mostrarMensajeDeError(err);
      });
  }

  ordenarComisionesPorNombre(comisiones){
    var comisionesOrdenadas = comisiones.sort(function(o1,o2) {
      if (o1.nombre > o2.nombre) {
        return 1;
      } else if (o1.nombre < o2.nombre) {
        return -1;
      } 
      return 0;
    });
    return comisionesOrdenadas
  }

  quitarComisionDeOferta(idComision){
    const mensaje = '¿Está seguro de que desea quitar la comisión seleccionada de la oferta académica?';		
    this.utilesService.mostrarDialogoConfirmacion(mensaje).subscribe(confirma => {
      if (confirma) {
       this.quitarComision(idComision);
      }
   });
  }

  quitarComision(idComision) {
   this.restService.quitarComisionDeOferta(idComision, this.oferta.id).subscribe(res => {		
     this.utilesService.mostrarMensaje('La comisión fue removida con éxito');		
     this.getComisiones(this.oferta.id);
   },
   (err: HttpErrorResponse) => {
       this.utilesService.mostrarMensajeDeError(err);
   });
 }

}
