import { Component, Inject, OnInit } from '@angular/core';
import { UtilesService } from '../utiles.service';
import { RestService } from '../rest.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Materia } from '../materias/materia.model';
import { MatDialogRef, MatOptionSelectionChange, MAT_DIALOG_DATA } from '@angular/material';
import { Equivalencia } from '../equivalencias/equivalencia.model';
import { Observable } from 'rxjs';
import { startWith, map } from 'rxjs/operators';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { DataDialogo } from './data-dialogo.model';
import { AppMensajes } from '../app-mensajes.model';
import { AppRutas } from '../app-rutas.model';

@Component({
    selector: 'app-equivalencia-dialogo',
    templateUrl: './equivalencia-dialogo.component.html',
    styleUrls: ['../dialogo-abm.component.css']
})

export class EquivalenciaDialogoComponent implements OnInit {
  materias: Materia[];
  filtroMateriasOrigen: Observable<Materia[]>;
  filtroMateriasDestino: Observable<Materia[]>;
  form: FormGroup;
  equivalencia: Equivalencia;

  constructor(
    private utilesService: UtilesService,
    private restService: RestService,
    private fb: FormBuilder,
    private dialogRef: MatDialogRef<EquivalenciaDialogoComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DataDialogo) {
      this.equivalencia = data.equivalencia;
    }

  ngOnInit() {
    this.getMaterias();
    this.crearFormularioEquivalencia();
    this.insertarInformacionDeEquivalenciaEnFormulario();
  }

  insertarInformacionDeEquivalenciaEnFormulario(){
    if (this.equivalencia != null) {
      this.form.setValue({
          'materiaOrigen': this.equivalencia.materiaOrigen.nombre,
          'materiaDestino': this.equivalencia.materiaDestino.nombre
        });
  }
}

  crearFormularioEquivalencia() {
    this.form = this.fb.group({
        materiaOrigen: ['', Validators.required],
        materiaDestino: ['', Validators.required],
    });
}

  getMaterias() {
    this.restService.getMaterias().subscribe(materias => {
      this.guardarMaterias(materias);
    },
      (err: HttpErrorResponse) => {
        this.utilesService.mostrarMensajeDeError(err);
      });
    }

    guardarMaterias(materias: Materia[]){
      if (materias.length == 0) {
        this.irATarerasUsuario(AppMensajes.N0_HAY_MATERIAS_CARGADAS);

      } else {
        this.materias = materias;
        this.crearFiltroMateriasOrigen();
        this.crearFiltroMateriasDestino();
      }
    }


    irATarerasUsuario(mensaje){
      this.cerrar();
      this.utilesService.mostrarMensajeYRedireccionar(mensaje, AppRutas.TAREAS_USUARIO);
  }

    crearFiltroMateriasOrigen() {
        this.filtroMateriasOrigen = this.form.controls['materiaOrigen'].valueChanges.pipe(
            startWith(''),
            map(val => this.filtrarMaterias(val))
          );
    }

    crearFiltroMateriasDestino() {
      this.filtroMateriasDestino = this.form.controls['materiaDestino'].valueChanges.pipe(
          startWith(''),
          map(val => this.filtrarMaterias(val))
        );
  }

  filtrarMaterias(val: string): Materia[] {
    return this.materias.filter(option => {
      return option.nombre.toLowerCase().match(val.toLowerCase());
    });
  }


  guardar() {
    if(this.form.valid) {
      this.armarEquivalencia();
    } else{
      this.utilesService.validateAllFormFields(this.form);
    }
  }

  armarEquivalencia(){
    const { materiaOrigen, materiaDestino} = this.form.value;
    if(this.hayMateriaValida(materiaOrigen) && this.hayMateriaValida(materiaDestino)) {
      const equivalencia = new Equivalencia();
      equivalencia.materiaOrigen = this.utilesService.obtenerMateria(this.materias, materiaOrigen);
      equivalencia.materiaDestino = this.utilesService.obtenerMateria(this.materias, materiaDestino);
      this.dialogRef.close(equivalencia);
    }
  }

  hayMateriaValida(materia) {
    return this.utilesService.materiaValida(this.materias,materia);
  }

  cerrar() {
    this.dialogRef.close();
  }
 }