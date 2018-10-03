import { HttpErrorResponse } from '@angular/common/http';
import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { Carrera } from '../carreras/carrera.model';
import { Materia } from '../materias/materia.model';
import { RestService } from '../rest.service';
import { UtilesService } from '../utiles.service';
import { DataDialogo } from './data-dialogo.model';

@Component({
    selector: 'app-modificacion-de-materia-dialogo',
    templateUrl: './modificacion-de-materia-dialogo.component.html',
    styleUrls: ['./modificacion-de-materia-dialogo.component.css']
})
export class ModificacionDeMateriaDialogoComponent implements OnInit {
    materia: Materia;
    carreras: Carrera[];
    carrerasSeleccionadas: Carrera[] = [];
    checked = false;
    carreraChecked = false;
    form: FormGroup;

    constructor(
        private fb: FormBuilder,
        private utilesService: UtilesService,
        private restService: RestService,
        private dialogRef: MatDialogRef<ModificacionDeMateriaDialogoComponent>,
        @Inject(MAT_DIALOG_DATA) public data: DataDialogo) {
        this.materia = data.materia;
        this.carrerasSeleccionadas = data.materia.carreras;
    }

    ngOnInit() {
        this.getCarreras();
        this.crearFormularioMateria();
        this.insertarInformacionDeLaMateriaEnFormulario();
    }

    getCarreras() {
        this.restService.getCarreras().subscribe(carreras => {
            this.carreras = carreras;
        },
            (err: HttpErrorResponse) => {
                this.utilesService.mostrarMensajeDeError(err);
            });
    }

    crearFormularioMateria() {
        this.form = this.fb.group({
            codigo: ['', Validators.required],
            nombre: ['', Validators.required],
            horas: ['', Validators.required]
        });
    }

    onChange(carrera, $event) {
        if ($event.checked) {
            this.carrerasSeleccionadas.push(carrera);
        }
        else {
            this.carrerasSeleccionadas.forEach(carreraSeleccionada => {
                if (carrera.id == carreraSeleccionada.id) {
                    this.carrerasSeleccionadas.splice(this.carrerasSeleccionadas.indexOf(carreraSeleccionada), 1);
                }
            });
        }
    }

    verificarSeleccion(carrera) {
        this.carreraChecked = false;
        this.carrerasSeleccionadas.forEach(carreraSeleccionada => {
            if (carrera.id == carreraSeleccionada.id) {
                this.carreraChecked = true;
            }
        });
        return this.carreraChecked;
    }

    insertarInformacionDeLaMateriaEnFormulario() {
        if (this.materia != null) {
            this.form.setValue({
                'codigo': this.materia.codigo,
                'nombre': this.materia.nombre,
                'horas': this.materia.horas
            });
            this.checked = this.materia.estado;
        } else {
            this.form.setValue({
                'codigo': '',
                'nombre': '',
                'horas': ''
            });
            this.checked = this.materia.estado;
        }

    }

    guardar() {
        if (this.form.valid) {
            const { codigo, nombre, horas } = this.form.value;
            const materia = new Materia(codigo, nombre, this.carrerasSeleccionadas, this.checked, horas);
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
                'codigo': '',
                'nombre': '',
                'horas': ''
            });
        }
    }

    guardar() {
        if (this.form.valid) {
            const { codigo, nombre, horas } = this.form.value;
            const materia = new Materia(codigo, nombre, this.carrerasSeleccionadas, this.checked, horas);
            this.dialogRef.close(materia);
        }
    }

    cerrar() {
        this.dialogRef.close();
    }
}