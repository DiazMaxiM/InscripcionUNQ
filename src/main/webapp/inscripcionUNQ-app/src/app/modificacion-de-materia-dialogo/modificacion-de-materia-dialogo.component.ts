import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { Materia } from '../materias/materia.model';
import { Carrera } from '../carreras/carrera.model';
import { DataDialogo } from './data-dialogo.model';

@Component({
     selector: 'app-modificacion-de-materia-dialogo',
     templateUrl: './modificacion-de-materia-dialogo.component.html',
     styleUrls: ['./modificacion-de-materia-dialogo.component.css']
 })
 export class ModificacionDeMateriaDialogoComponent implements OnInit{
    materia: Materia;
    carreras: Carrera[];
    checked = false;
    form: FormGroup;

    constructor(
        private fb: FormBuilder,
        private dialogRef: MatDialogRef<ModificacionDeMateriaDialogoComponent>,
        @Inject(MAT_DIALOG_DATA) public data: DataDialogo ) {
            this.materia = data.materia;
            this.carreras = data.materia.carreras;
//            this.checked = data.materia.estado;
            console.log("Habilitada" + this.checked);
    }

    ngOnInit() {
      this.crearFormularioMateria();
      this.insertarInformacionDeLaMateriaEnFormulario();
    }
    
    crearFormularioMateria() {
        this.form = this.fb.group({
            codigo: ['', Validators.required],
            nombre: ['', Validators.required],
            carreras: ['', Validators.required],
            horas: ['', Validators.required]
        });
    }
    
    insertarInformacionDeLaMateriaEnFormulario() {
        if (this.materia != null) {
            this.form.setValue({
                'codigo': this.materia.codigo,
                'nombre': this.materia.nombre,
                'carreras': this.materia.carreras,
                'horas': this.materia.horas
            });
            this.checked = this.materia.estado;
        }

    }

    guardar() {
        if (this.form.valid) {
            const { codigo, nombre, carreras, horas} = this.form.value;
            const materia = new Materia(codigo, nombre, carreras, this.checked, horas, this.materia.id);
            this.dialogRef.close(materia);
        }
    }

    cerrar() {
        this.dialogRef.close();
    }
    onChange(carrera) {

    
  
    }
}
/*
import {Compoent, Inject, OnInit, ViewEncapsulation} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {FormBuilder, Validators, FormGroup} from '@angular/forms';
import { Carrera } from '../carreras/carrera.model';
import { DataDialogo } from './data-dialogo.model';
import { UtilesService } from '../utiles.service';

@Component({
    selector: 'app-carrera-dialogo',
    templateUrl: './carrera-dialogo.component.html',
    styleUrls: ['../dialogo-abm.component.css']
})
export class CarreraDialogoComponent implements OnInit {

    form: FormGroup;
    carrera: Carrera;
    checked = false;

    constructor(
        private fb: FormBuilder,
        private utilesService: UtilesService,
        private dialogRef: MatDialogRef<CarreraDialogoComponent>,
        @Inject(MAT_DIALOG_DATA) public data: DataDialogo ) {
            this.carrera = data.carrera;
    }
    ngOnInit() {
        this.crearFormularioCarrera();
        this.insertarInformacionDeLaCarreraEnFormulario();

    }

    crearFormularioCarrera() {
        this.form = this.fb.group({
            codigo: ['', Validators.required],
            descripcion: ['', Validators.required]
        });
    }

    insertarInformacionDeLaCarreraEnFormulario() {
        if (this.carrera != null) {

            this.form.setValue({
                'codigo': this.carrera.codigo,
                'descripcion': this.carrera.descripcion,
              });
            this.checked = this.carrera.habilitada;
        }
    }


    guardar() {
        if (this.form.valid) {
            const { codigo, descripcion} = this.form.value;
            const carrera = new Carrera(codigo, descripcion, this.checked);
            this.dialogRef.close(carrera);
        }
    }

    cerrar() {
        this.dialogRef.close();
    }

}

 */