import {Component, Inject, OnInit, ViewEncapsulation} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {FormBuilder, Validators, FormGroup} from '@angular/forms';
import { Carrera } from '../carreras/carrera.model';
import { DataDialogo } from './data-dialogo.model';

@Component({
    selector: 'app-carrera-dialogo',
    templateUrl: './carrera-dialogo.component.html',
    styleUrls: ['./carrera-dialogo.component.css']
})
export class CarreraDialogoComponent implements OnInit {

    form: FormGroup;
    carrera: Carrera;

    constructor(
        private fb: FormBuilder,
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
            descripcion: ['', Validators.required],
            estado: ['', Validators.required]
        });
    }

    insertarInformacionDeLaCarreraEnFormulario() {
        if (this.carrera != null) {
            this.form.setValue({
                'codigo': this.carrera.codigo,
                'descripcion': this.carrera.descripcion,
                'estado': this.carrera.estado
              });
        }
    }


    guardar() {
        if (this.form.valid) {
            const { codigo, descripcion, estado} = this.form.value;
            const carrera = new Carrera(codigo, descripcion, estado);
            this.dialogRef.close(carrera);
        }
    }

    cerrar() {
        this.dialogRef.close();
    }

}
