import { Component, OnInit } from '@angular/core';
import { RestService } from '../rest.service';
import { UtilesService } from '../utiles.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Materia } from '../materias-aprobadas/materia.model';
import { MatDialog, MatDialogConfig } from '@angular/material';
import { ModificacionDeMateriaDialogoComponent } from '../modificacion-de-materia-dialogo/modificacion-de-materia-dialogo.component';

@Component({
  selector: 'app-materias',
  templateUrl: './materias.component.html',
  styleUrls: ['./materias.component.css']
})
export class MateriasComponent implements OnInit {
  materias: Materia[];
  idMateria;

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

  editarMateria(materia) {
    this.abrirDialogoParaEditarMateria(materia);
  }

  abrirDialogoParaEditarMateria(materia) {
    const dialogoConfig = new MatDialogConfig();
    dialogoConfig.disableClose = true;
    dialogoConfig.autoFocus = true;
    dialogoConfig.width = '600px';
    dialogoConfig.height = '450px';
    dialogoConfig.data = {
      materia: materia,
    };

    const dialogRef = this.dialog.open(ModificacionDeMateriaDialogoComponent, dialogoConfig);

    dialogRef.afterClosed().subscribe(registro => {
      this.actualizarMateria(registro);
    });
  }

  actualizarMateria(materia: Materia) {
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

  eliminarMateria(idMateria) {
    const mensaje = '¿Está seguro de que desea eliminar la materia seleccionada?';
    this.utilesService.mostrarDialogoConfirmacion(mensaje).subscribe(confirma => {
      if (confirma) {
        this.eliminar(idMateria);
      }
    });
  }

  eliminar(idMateria) {
    this.restService.eliminarMateria(idMateria).subscribe(res => {
      this.utilesService.mostrarMensaje('Se ha eliminado la materia');
      this.getMaterias();
    },
      (err: HttpErrorResponse) => {
        this.utilesService.mostrarMensajeDeError(err);
      });
  }
}
