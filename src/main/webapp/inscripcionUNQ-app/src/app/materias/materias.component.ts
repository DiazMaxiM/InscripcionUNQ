import { Component, OnInit } from '@angular/core';
import { RestService } from '../rest.service';
import { UtilesService } from '../utiles.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Materia } from './materia.model';
import { MatDialog, MatDialogConfig } from '@angular/material';
import { ModificacionDeMateriaDialogoComponent } from '../modificacion-de-materia-dialogo/modificacion-de-materia-dialogo.component';

@Component({
  selector: 'app-materias',
  templateUrl: './materias.component.html',
  styleUrls: ['../estilo-abm.component.css']
})
export class MateriasComponent implements OnInit {
  materias: Materia[];
  idMateria;
  materiaBuscada: string;

  constructor(
    private restService: RestService,
    private utilesService: UtilesService,
    private dialog: MatDialog
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

  abrirDialogoParaLaCreacionDeMateria() {
    const dialogRef = this.crearConfiguracionDialogoParaMateria();

    dialogRef.afterClosed().subscribe( val => {
			this.getMaterias();
    });
  }

  abrirDialogoParaEdicionDeMateria(materia: Materia) {
    const dialogRef = this.crearConfiguracionDialogoParaMateria(materia);
    dialogRef.afterClosed().subscribe( val => {
			this.getMaterias();
		});
	}

  actualizarMateria(materia) {
    this.restService.actualizarInformacionMateria(materia)
      .subscribe(res => {
        const mensaje = 'Los datos de la materia fueron actualizados con éxito';
        this.utilesService.mostrarMensaje(mensaje);
        this.getMaterias();
      },
      (err: HttpErrorResponse) => {
        this.utilesService.mostrarMensajeDeError(err);
      });
  }

  crearConfiguracionDialogoParaMateria(materia?) {
    const dialogConfig = new  MatDialogConfig();

    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = false;
    dialogConfig.width = '600px';
    dialogConfig.height = '450px';
    dialogConfig.data = {
      materia: materia
    };

    const dialogRef = this.dialog.open(ModificacionDeMateriaDialogoComponent,
            dialogConfig);

    return dialogRef;
  }

  cambiarEstado(materia: Materia, evento) {
    materia.estado = evento.checked;
    this.actualizarMateria(materia);
  }
}