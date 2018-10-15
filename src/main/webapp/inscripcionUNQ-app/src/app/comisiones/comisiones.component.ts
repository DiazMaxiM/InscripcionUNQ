import { Component, OnInit } from '@angular/core';
import { RestService } from '../rest.service';
import { UtilesService } from '../utiles.service';
import { Periodo } from '../periodos/periodo.model';
import { Observable } from 'rxjs';
import { AppRutas } from '../app-rutas.model';
import { FormControl } from '@angular/forms';
import { startWith, map } from 'rxjs/operators';
import { AppMensajes } from '../app-mensajes.model';
import { MatOptionSelectionChange, MatDialogConfig, MatDialog } from '@angular/material';
import { ComisionDialogoComponent } from '../comision-dialogo/comision-dialogo.component';
import { Comision } from '../comisiones-de-oferta/comision.model';

@Component({
  selector: 'app-comisiones',
  templateUrl: './comisiones.component.html',
  styleUrls: ['../estilo-abm.component.css']
})

export class ComisionesComponent implements OnInit {

  periodoControl = new FormControl();
  comisiones: Comision[];
  periodos: Periodo[];
  filtroPeriodos: Observable<Periodo[]>;
  mostrarComisiones;
  comisionBuscada;
  comisionSeleccionada;

  constructor(
    private restService: RestService,
    private utilesService: UtilesService,
    private dialog: MatDialog
  ) { }

  ngOnInit() {
    this.getPeriodos();
  }

  getPeriodos() {
    this.restService.getPeriodos().subscribe(periodos => {
      this.periodos = periodos;
      this.guardarPeriodos(periodos);
    },
    (err) => {
        this.utilesService.mostrarMensajeDeError(err);
    });
}

guardarPeriodos(periodos: Periodo[]) {
    if (periodos.length == 0 ) {
      const mensaje = 'No se encontraron períodos para la oferta';
      this.utilesService.mostrarMensajeYRedireccionar(mensaje,  AppRutas.TAREAS_USUARIO);
    }
    this.periodos = periodos;
    this.crearFiltroPeriodos();
 }

 crearFiltroPeriodos() {

    this.filtroPeriodos = this.periodoControl.valueChanges.pipe(
        startWith(''),
        map(val => this.filtrarPeriodo(val))
      );
}

filtrarPeriodo(val: string): Periodo[] {
    return this.periodos.filter(option => {
      return option.codigo.toLowerCase().match(val.toLowerCase());
    });
  }

periodoSeleccionado(event: MatOptionSelectionChange, periodo: Periodo) {
  if (event.source.selected) {
    this.getComisionesEnPeriodo(periodo);
  }
}

getComisionesEnPeriodo(periodo) {
    this.restService.getComisionesEnPeriodo(periodo.id).subscribe(comisiones => {
      this.guardarComisiones(comisiones);
    },
    (err) => {
        this.utilesService.mostrarMensajeDeError(err);
    });
}

guardarComisiones(comisiones) {
    if (comisiones.length == 0) {
      this.utilesService.mostrarMensaje(AppMensajes.NO_SE_ENCONTRARON_COMISIONES_EN_PERIODO);
      this.mostrarComisiones = false;
      this.comisiones = [];

    } else {
      this.comisiones = this.utilesService.ordenarComisionesPorNombre(comisiones);
      this.mostrarComisiones = true;
    }
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
     this.guardarNuevaComision(comision);
  }
}

guardarComisionModificada(comision: Comision) {
  comision.id = this.comisionSeleccionada.id;
  this.restService.actualizarInformacionDeComision(comision).subscribe(rest => {
    this.mostarComisionEnPeriodo(comision.periodo);
  },
  (err) => {
      this.utilesService.mostrarMensajeDeError(err);
  });
 }


guardarNuevaComision(comision: Comision) {
  this.restService.crearNuevaComision(comision).subscribe(rest => {
    this.mostarComisionEnPeriodo(comision.periodo);
  },
  (err) => {
      this.utilesService.mostrarMensajeDeError(err);
  });
}

mostarComisionEnPeriodo(periodo) {
  this.periodoControl = new FormControl(periodo.codigo);
  this.getComisionesEnPeriodo(periodo);
}

crearConfiguracionDialogoParaComision(comision?) {
 const dialogConfig = new  MatDialogConfig();
  dialogConfig.disableClose = true;
 dialogConfig.autoFocus = false;
 dialogConfig.width = 'auto';
 dialogConfig.height = 'auto';
 dialogConfig.data = {
   comision: comision
 };
  const dialogRef = this.dialog.open(ComisionDialogoComponent,
         dialogConfig);
  return dialogRef;
}


eliminarComision(comision: Comision){
    const mensaje = '¿Está seguro de que desea eliminar la comisión seleccionada?';
    this.utilesService.mostrarDialogoConfirmacion(mensaje).subscribe(confirma => {
      if (confirma) {
       this.eliminar(comision);
      }
   });
  }


eliminar(comision: Comision) {
  this.restService.eliminarComision(comision.id).subscribe(res => {
    this.utilesService.mostrarMensaje('La comisión fue eliminada con éxito');
    this.getComisionesEnPeriodo(comision.periodo);
  },
  (err) => {
      this.utilesService.mostrarMensajeDeError(err);
  });
}


}
