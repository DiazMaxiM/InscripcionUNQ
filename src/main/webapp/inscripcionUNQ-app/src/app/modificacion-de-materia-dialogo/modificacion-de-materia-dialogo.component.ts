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
    form: FormGroup;

    constructor(
        private fb: FormBuilder,
        private dialogRef: MatDialogRef<ModificacionDeMateriaDialogoComponent>,
        @Inject(MAT_DIALOG_DATA) public data: DataDialogo ) {
            this.materia = data.materia;
            this.carreras = data.materia.carreras;
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
            horas: ['', Validators.required],
            estado: ['', Validators.required]
        });
    }
    
    insertarInformacionDeLaMateriaEnFormulario() {
        if (this.materia != null) {
            this.form.setValue({
                'codigo': this.materia.codigo,
                'nombre': this.materia.nombre,
                'carreras': this.materia.carreras,
                'horas': this.materia.horas,
                'estado': this.materia.estado
            });
        }
    }

    guardar() {
        if (this.form.valid) {
            const { codigo, nombre, carreras, horas, estado} = this.form.value;
            const materia = new Materia(codigo, nombre, carreras, estado, horas, this.materia.id);
            this.dialogRef.close(materia);
        }
    }

    cerrar() {
        this.dialogRef.close();
    }
}