import {Component, Inject, OnInit, ViewEncapsulation} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {FormBuilder, Validators, FormGroup} from '@angular/forms';
import { Carrera } from '../carreras/carrera.model';
import { UtilesService } from '../utiles.service';
import { Comision } from './comision.model';
import { DataDialogo } from './data-dialogo.model';

@Component({
    selector: 'app-comision-materia-dialogo',
    templateUrl: './comision-materia-dialogo.component.html',
    styleUrls: ['../dialogo-abm.component.css']
})

export class ComisionMateriaDialogoComponent implements OnInit {

    form: FormGroup;
    comision: Comision;


    constructor(
        private fb: FormBuilder,
        private utilesService: UtilesService,
        private dialogRef: MatDialogRef<ComisionMateriaDialogoComponent>,
        @Inject(MAT_DIALOG_DATA) public data: DataDialogo ) {

            if (data != null) {
                this.comision = data.comision;
            }
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
        if (this.comision != null) {

        }
    }


    guardar() {
        if (this.form.valid) {
        }
    }

    cerrar() {
        this.dialogRef.close();
    }

}
