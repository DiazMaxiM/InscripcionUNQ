import { Component, OnInit} from '@angular/core';
import { RestService } from '../rest.service';
import {UtilesService} from '../utiles.service';
import {HttpErrorResponse } from '@angular/common/http';
import { MatDialog, MatDialogConfig } from '@angular/material';
import { OfertaAcademica } from './oferta-academica.model';
import { OfertaAcademicaDialogoComponent } from '../oferta-academica-dialogo/oferta-academica.dialogo.component';

@Component({
  selector: 'app-oferta-academica',
  templateUrl: './oferta-academica.component.html',
  styleUrls: ['./oferta-academica.component.css', '../estilo-abm.component.css']
})
export class ofertaAcademicaComponent implements OnInit {

    ofertas: OfertaAcademica[];

  constructor(
    private restService: RestService,
    private utilesService: UtilesService,
    private dialog: MatDialog
) { }

  ngOnInit() {
    this.ofertas = JSON.parse(localStorage.getItem('ofertas'));
  }

  getOfertas() {
    this.restService.getOfertas().subscribe(ofertas => {
      this.ofertas = ofertas;
      console.log(ofertas);
    },
    (err: HttpErrorResponse) => {
        this.utilesService.mostrarMensajeDeError(err);
    });
  }

  eliminarOferta(idOferta) {
    this.restService.eliminarOferta(idOferta).subscribe(res => {
      this.utilesService.mostrarMensaje('La oferta fue eliminada con exito');
      this.getOfertas();
    },
    (err: HttpErrorResponse) => {
        this.utilesService.mostrarMensajeDeError(err);
    });
  }

  clonarOferta(oferta) {
    this.restService.clonarOferta(oferta).subscribe(res => {
      this.utilesService.mostrarMensaje('La oferta fue clonada con exito');
      this.getOfertas();
    },
    (err: HttpErrorResponse) => {
        this.utilesService.mostrarMensajeDeError(err);
    });
  }

  abrirDialogoParaCreacionDeOferta() {
    const dialogRef = this.crearConfiguracionDialogoParaOferta();
    dialogRef.afterClosed().subscribe( val => {
      if ( val != undefined) {
        this.crearNuevaOferta(val);
      }
    });

  }

  crearNuevaOferta(oferta: OfertaAcademica) {
    this.restService.crearNuevaOferta(oferta)
    .subscribe(res => {
     const mensaje = 'Se creó la nueva oferta académica con exito';
      this.utilesService.mostrarMensaje(mensaje);
      this.getOfertas();
    },
    (err: HttpErrorResponse) => {
      this.utilesService.mostrarMensajeDeError(err);
    });
  }

  crearConfiguracionDialogoParaOferta(oferta?) {
    const dialogConfig = new  MatDialogConfig();

    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = false;
    dialogConfig.width = '600px';
    dialogConfig.height = '450px';
    dialogConfig.data = {
      oferta: oferta
    };

    const dialogRef = this.dialog.open(OfertaAcademicaDialogoComponent, dialogConfig);

    return dialogRef;
  }

  abrirDialogoParaEdicionDeOferta(oferta) {
    console.log(oferta);
    const dialogRef = this.crearConfiguracionDialogoParaOferta(oferta);
    dialogRef.afterClosed().subscribe( val => {
      if (val != undefined) {
        this.actualizarOfertaSeleccionada(val, oferta.id);
      }
    });

  }

  actualizarOfertaSeleccionada(oferta: OfertaAcademica, idOferta) {
    oferta.id = idOferta;
    this.actualizarOferta(oferta);
  }

  actualizarOferta(oferta) {
    console.log(oferta);
    this.restService.actualizarInformacionDeOferta(oferta)
    .subscribe(res => {
     const mensaje = 'Los datos de la oferta académica fueron actualizados con exito';
      this.utilesService.mostrarMensaje(mensaje);
      this.getOfertas();
    },
    (err: HttpErrorResponse) => {
      this.utilesService.mostrarMensajeDeError(err);
    });

  }

  cambiarEstado(oferta, evento) {
    oferta.habilitada = evento.checked;
    this.actualizarOferta(oferta);

  }



}
