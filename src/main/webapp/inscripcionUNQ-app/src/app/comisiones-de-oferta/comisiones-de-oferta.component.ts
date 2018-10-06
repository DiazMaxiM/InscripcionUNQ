import { Component, OnInit } from '@angular/core';
import { RestService } from '../rest.service';
import { UtilesService } from '../utiles.service';
import { Comision } from './comision.model';
import { MatDialogConfig, MatDialog } from '@angular/material';
import { ComisionMateriaDialogoComponent } from '../comision-material-dialogo/comision-materia-dialogo.component';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-comisiones-de-oferta',
  templateUrl: './comisiones-de-oferta.component.html',
  styleUrls: ['../estilo-abm.component.css']
})

export class ComisionesDeOfertaComponent implements OnInit {
  comisiones: Comision[];
  idOferta;

  constructor(
    private restService: RestService,
    private utilesService: UtilesService,
    private dialog: MatDialog
  ) { }

  ngOnInit() {
    this.comisiones = JSON.parse(localStorage.getItem('comisiones-de-oferta'));
  }

  abrirDialogoParaAgregarComision(comision) {
    const dialogRef = this.crearConfiguracionDialogoParaComision();
  }

  abrirDialogoParaVerComisiones(){
  }


  crearConfiguracionDialogoParaComision() {
    const dialogConfig = new  MatDialogConfig();

    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = false;
    dialogConfig.width = '600px';
    dialogConfig.height = '450px';

    const dialogRef = this.dialog.open(ComisionMateriaDialogoComponent,
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
