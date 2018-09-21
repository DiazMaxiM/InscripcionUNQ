import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig} from '@angular/material';
import { SeleccionDeComisionDialogoComponent} from '../seleccion-de-comision-dialogo/seleccion-de-comision-dialogo.component';
import { RestService } from '../rest.service';
import { ComisionSeleccionada } from '../seleccion-de-comision-dialogo/comision-seleccionada.model';
import {PageEvent} from '@angular/material';
import {Subject} from '../subject/subject.model';
import {RegistroDeComisionesSeleccionadasService} from './registro-de-comisiones-seleccionadas.service';
import {UtilesService} from '../utiles.service';
import {Comision} from './comision.model';

@Component({
  selector: 'app-seleccion-de-materias-por-cursar',
  templateUrl: './seleccion-de-materias-por-cursar.component.html',
  styleUrls: ['./seleccion-de-materias-por-cursar.component.css']
})

export class SeleccionDeMateriasPorCursarComponent implements OnInit {

    materiasDisponibles: Subject[] = [];

    // MatPaginator Output
    pageEvent: PageEvent;
    // MatPaginator Inputs
   length = 0;
   pageSize = 10;
   pageIndex = 0;
   pageSizeOptions: number[] = [5, 10];
   materiasDisponiblesActivas: Subject[] = [];
   comisionesSeleccionadas: ComisionSeleccionada[] = [];
   idEstudiante: string;

  constructor(
    private restService: RestService,
    private dialog: MatDialog,
    private registroComisionesService: RegistroDeComisionesSeleccionadasService,
    private utilesService: UtilesService
  ) {}

ngOnInit() {
     this.idEstudiante = localStorage.getItem('idEstudiante');
     this.limpiarInformacionComisionesSeleccionadas();
     this.obtenerMateriasDisponibles();
  }

  obtenerMateriasDisponibles() {
     this.restService.obtenerMateriasDisponibles(this.idEstudiante)
     .subscribe(materiasDisponibles => {
       this.materiasDisponibles = materiasDisponibles;
       this.length = this.materiasDisponibles.length;
       this.materiasDisponiblesActivas = this.materiasDisponibles.slice(0, this.pageSize);
       this.marcarMateriasAnteriormenteSeleccionadas();
     });
   }

onPageChanged(e) {
  this.pageIndex = e.pageIndex;
  this.pageSize = e.pageSize;
  this.updatePagination(e.pageIndex, e.pageSize);
}

updatePagination(pageIndex, pageSize) {
  const firstCut = pageIndex * pageSize;
  const secondCut = firstCut + pageSize;
  this.materiasDisponiblesActivas = this.materiasDisponibles.slice(firstCut, secondCut);
}
setPageSizeOptions(setPageSizeOptionsInput: string) {
  this.pageSizeOptions = setPageSizeOptionsInput.split(',').map(str => +str);
}

materiaSeleccionada(materia, checkbox) {
  if (checkbox.checked) {
    this.agregarComisionSeleccionada(materia);
  } else {
    this.eliminarComisionSeleccionada(materia.id);
    this.deseleccionarMateria(materia);
  }
}

agregarComisionSeleccionada(materia) {
  const comisiones: any[] = materia.commissionsJson;
  if (comisiones.length > 1) {
    this.abrirDialogoParaSeleccionarComision(materia);
  } else {
    const comision = comisiones[0];
    const registro = this.registroComisionesService.crearRegistroDeComisionSeleccionada(materia.id,comision);
    this.guardarRegistro(materia,registro);
  }
}

mostrarInformacionDelaComisionSeleccionada(materia, comisionSeleccionada: ComisionSeleccionada) {
    const materiaActualizada = this.materiaActualizada(materia,comisionSeleccionada.nombreDeLaComision,comisionSeleccionada.horariosSeleccionados, true);
    this.materiasDisponibles[this.materiasDisponibles.indexOf(materia)] = materiaActualizada;
    this.updatePagination(this.pageIndex,  this.pageSize);
  }

materiaActualizada(oldSubject, commissionName, horariosSeleccionados, checked) {
    return {
         'id': oldSubject.id,
         'code': oldSubject.code,
         'name': oldSubject.name,
         'approved': oldSubject.approved,
         'checked': checked,
         'commissionName': commissionName,
         'horariosSeleccionados': horariosSeleccionados,
         'commissionsJson': oldSubject.commissionsJson
       };
    }

abrirDialogoParaSeleccionarComision(materia) {
      const dialogoConfig = new MatDialogConfig();
      dialogoConfig.disableClose = true;
      dialogoConfig.autoFocus = true;
      dialogoConfig.data = {
          materia: materia,
        };
      const dialogRef = this.dialog.open(SeleccionDeComisionDialogoComponent, dialogoConfig);
      dialogRef.afterClosed().subscribe(registro => {
           this.guardarRegistro(materia, registro);
      });
}

deseleccionarMateria(materia) {
  const materiaActualizada = this.materiaActualizada(materia, '', [], false);
  this.materiasDisponibles[this.materiasDisponibles.indexOf(materia)] = materiaActualizada;
  this.updatePagination(this.pageIndex, this.pageSize);
}

guardarRegistro(materia, registro: ComisionSeleccionada) {
  if (registro != null && registro.horariosSeleccionados.length > 0) {
        this.mostrarInformacionDelaComisionSeleccionada(materia, registro);
        this.comisionesSeleccionadas.push(registro);
  } else{
    if (registro != null && registro.horariosSeleccionados.length == 0){
      this.utilesService.mostrarMensaje('No se pueden cursar materias en horarios que se superponen');
    }
    this.deseleccionarMateria(materia);
  }
}

eliminarComisionSeleccionada(idMateria) {
  const registro = this.comisionesSeleccionadas.find(function (registroDeComision) {
           return registroDeComision.idMateria == idMateria;
  });
  const index = this.comisionesSeleccionadas.indexOf(registro);
  this.comisionesSeleccionadas.splice(index, 1);
  this.registroComisionesService.eliminarHorarios(registro.horariosSeleccionados);
}

finalizarEncuesta(){
  if(this.comisionesSeleccionadas.length == 0){
    this.utilesService.mostrarMensaje('No ha seleccionado ninguna materia')
  } else {
     this.enviarComisionesSeleccionadas();
  }
}

enviarComisionesSeleccionadas(){
  const comisiones= [];
  for (const comision of this.comisionesSeleccionadas){
    comisiones.push(new Comision(String(comision.idComision)));
  }
  this.restService.enviarComisionesSeleccionadas(this.idEstudiante, comisiones).subscribe(data => {
    this.utilesService.irA('encuesta-finalizada');
  });
}

marcarMateriasAnteriormenteSeleccionadas(){
  for(const materia of this.materiasDisponibles){
    if(materia.comisionRegistrado != null){
      const comisionSeleccionada = this.registroComisionesService.crearRegistroDeComisionSeleccionada(materia.id, materia.comisionRegistrado);
      this.guardarRegistro(materia, comisionSeleccionada);
    }
  }

}
limpiarInformacionComisionesSeleccionadas() {
  this.comisionesSeleccionadas = [];
  this.registroComisionesService.limpiarHorarios();
}

}
