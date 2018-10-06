import {Component, Inject, OnInit, ViewEncapsulation} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {FormBuilder, Validators, FormGroup} from '@angular/forms';
import { Comision } from '../comisiones-de-oferta/comision.model';
import { UtilesService } from '../utiles.service';
import { DataDialogo } from './data-dialogo.model';
import { Materia } from '../materias/materia.model';
import { Observable } from 'rxjs';
import { startWith, map } from 'rxjs/operators';
import { OfertaAcademica } from '../oferta-academica/oferta-academica.model';
import { RestService } from '../rest.service';

@Component({
    selector: 'app-comision-oferta-dialogo',
    templateUrl: './comision-oferta-dialogo.component.html',
    styleUrls: ['../dialogo-abm.component.css']
})

export class ComisionOfertaDialogoComponent implements OnInit {

    form: FormGroup;
    comision: Comision;
    materias: Materia[];
    filtroMaterias: Observable<Materia[]>;
    materiaParaComision: Materia;


    constructor(
        private fb: FormBuilder,
        private utilesService: UtilesService,
        private dialogRef: MatDialogRef<ComisionOfertaDialogoComponent>,
        private restService: RestService,
        @Inject(MAT_DIALOG_DATA) public data: DataDialogo ) {
                this.comision = data.comision;
    }
    ngOnInit() {
        this.crearFormularioComision();
        this.insertarInformacionDeComisioEnFormulario();
        this.getMaterias();

    }

    crearFiltroMaterias() {

        this.filtroMaterias = this.form.controls['materia'].valueChanges.pipe(
            startWith(''),
            map(val => this.filtrarMaterias(val))
          );
    }

    filtrarMaterias(val: string): Materia[] {
        return this.materias.filter(option => {
          return option.nombre.toLowerCase().match(val.toLowerCase());
        });
      }

    getMaterias() {
        const oferta: OfertaAcademica = JSON.parse(localStorage.getItem('oferta-seleccionada')) ;
        const idCarrera = oferta.carrera.id;
        this.restService.getMateriasDeCarrera(idCarrera).subscribe(materias => {
          this.materias = materias;
          this.crearFiltroMaterias();
        },
        (err) => {
            this.utilesService.mostrarMensajeDeError(err);
        });
      }


    crearFormularioComision() {
        this.form = this.fb.group({
            nombre: ['', Validators.required],
            cupo: ['', Validators.required],
            materia: ['', Validators.required]
        });
    }

    insertarInformacionDeComisioEnFormulario() {
        if (this.comision != null) {
            this.form.setValue({
                'nombre': this.comision.nombre,
                'cupo': this.comision.cupo,
                'materia': this.comision.nombreMateria
              });

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
