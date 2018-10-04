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

  abrirDialogoParaLaCreacionDeMateria() {
    const dialogRef = this.crearConfiguracionDialogoParaMateria();

    dialogRef.afterClosed().subscribe( val => {
      if ( val != undefined) {
        this.crearNuevaMateria(val);
      }
    });
  }

  crearNuevaMateria(materia: Materia) {
    this.restService.agregarNuevaMateria(materia)
    .subscribe(res => {
     const mensaje = 'Se creó la nueva materia con exito';
      this.utilesService.mostrarMensaje(mensaje);
      this.getMaterias();
    },
    (err: HttpErrorResponse) => {
      this.utilesService.mostrarMensajeDeError(err);
    });
  }

  abrirDialogoParaEdicionDeMateria(materia: Materia) {
    const dialogRef = this.crearConfiguracionDialogoParaMateria(materia);
    dialogRef.afterClosed().subscribe( val => {
      if (val != undefined) {
        this.actualizarMateriaSeleccionada(val, materia.id);
      }
    });
}

  actualizarMateriaSeleccionada(materia: Materia, idMateria) {
    materia.id = idMateria;
    this.actualizarMateria(materia);
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
      this.utilesService.mostrarMensaje('La materia fue eliminada con éxito');
      this.getMaterias();
    },
      (err: HttpErrorResponse) => {
        this.utilesService.mostrarMensajeDeError(err);
      });
  }

  cambiarEstado(materia: Materia, evento) {
    materia.estado = evento.checked;
    this.actualizarMateria(materia);
  }
}