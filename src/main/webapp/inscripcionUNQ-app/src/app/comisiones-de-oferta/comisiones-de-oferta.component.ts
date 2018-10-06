import { Component, OnInit } from '@angular/core';
import { RestService } from '../rest.service';
import { UtilesService } from '../utiles.service';
import { Comision } from './comision.model';
import { MatDialogConfig, MatDialog } from '@angular/material';
import { HttpErrorResponse } from '@angular/common/http';
import { ComisionOfertaDialogoComponent } from '../comision-oferta-dialogo/comision-oferta-dialogo.component';

@Component({
  selector: 'app-comisiones-de-oferta',
  templateUrl: './comisiones-de-oferta.component.html',
  styleUrls: ['../estilo-abm.component.css']
})

export class ComisionesDeOfertaComponent implements OnInit {
  comisiones: Comision[];
  idOferta;
  comisionSeleccionada;

  constructor(
    private restService: RestService,
    private utilesService: UtilesService,
    private dialog: MatDialog
  ) { }

  ngOnInit() {
    this.comisiones = JSON.parse(localStorage.getItem('comisiones-de-oferta'));
  }

  abrirDialogoComision(comision?: Comision) {
    this.comisionSeleccionada = comision;
    const dialogRef = this.crearConfiguracionDialogoParaComision(comision);
    dialogRef.afterClosed().subscribe( val => {
      if (val != undefined) {
        this.guardarComision(val);
      }
    });
  }

  guardarComision(comision: Comision) {
    if(this.comisionSeleccionada != null ) {
       this.guardarComisionModificada(comision);
    } else {
       this.guardarNuevaComision(comision)
    }
  }
  guardarComisionModificada(comision: Comision) {
    comision.idComision = this.comisionSeleccionada.id;

  }

  guardarNuevaComision(comision: Comision) {

  }

  crearConfiguracionDialogoParaComision(comision?) {
    const dialogConfig = new  MatDialogConfig();

    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = false;
    dialogConfig.width = '800px';
    dialogConfig.height = '600px';
    dialogConfig.data = {
      comision: comision
    };


    const dialogRef = this.dialog.open(ComisionOfertaDialogoComponent,
            dialogConfig);

    return dialogRef;
  }


  getComisiones(id) {
    this.restService.getComisionesDeOferta(id).subscribe(comisiones => {
      this.comisiones = comisiones;
    },
      (err: HttpErrorResponse) => {
        this.utilesService.mostrarMensajeDeError(err);
      });
  }

}
