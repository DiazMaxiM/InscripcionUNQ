import { Component, OnInit } from '@angular/core';
import { RestService } from '../rest.service';
import { UtilesService } from '../utiles.service';
import { Materia } from '../materias/materia.model';
import { MatDialogConfig, MatDialog } from '@angular/material';
import { ComisionMateriaDialogoComponent } from '../comision-material-dialogo/comision-materia-dialogo.component';

@Component({
  selector: 'app-materias-de-oferta',
  templateUrl: './materias-de-oferta.component.html',
  styleUrls: ['../estilo-abm.component.css']
})

export class MateriasDeOfertaComponent implements OnInit {
  materias: Materia[];
  idMateria;

  constructor(
    private restService: RestService,
    private utilesService: UtilesService,
    private dialog: MatDialog
  ) { }

  ngOnInit() {
    this.materias = JSON.parse(localStorage.getItem('materias-de-oferta'));
  }

  abrirDialogoParaAgregarComision(materia) {
    const dialogRef = this.crearConfiguracionDialogoParaComision();
  }

  crearNuevaComision(materia: Materia) {
    localStorage.setItem('materiaSeleccionada', JSON.stringify(materia));
    const dialogRef = this.crearConfiguracionDialogoParaComision();
    dialogRef.afterClosed().subscribe( val => {
      if (val != undefined) {
      }
    });

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

}
