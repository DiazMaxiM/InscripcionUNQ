import {Component, Inject, OnInit, ViewEncapsulation} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {FormBuilder, Validators, FormGroup, FormControl} from '@angular/forms';
import { DataDialogo } from './data-dialogo.model';
import { UtilesService } from '../utiles.service';
import { OfertaAcademica } from '../oferta-academica/oferta-academica.model';
import {Observable} from 'rxjs';
import {map, startWith} from 'rxjs/operators';
import { Carrera } from '../carreras/carrera.model';
import { RestService } from '../rest.service';

@Component({
    selector: 'app-oferta-academica-dialogo',
    templateUrl: './oferta-academica-dialogo.component.html',
    styleUrls: ['../dialogo-abm.component.css']
})
export class OfertaAcademicaDialogoComponent implements OnInit {

    form: FormGroup;
    oferta: OfertaAcademica;
    checked = true;
    carreras: Carrera[];
    filtroCarreras: Observable<Carrera[]>;
    carreraParaOferta: Carrera;


    constructor(
        private fb: FormBuilder,
        private utilesService: UtilesService,
        private restService: RestService,
        private dialogRef: MatDialogRef<OfertaAcademicaDialogoComponent>,
        @Inject(MAT_DIALOG_DATA) public data: DataDialogo ) {
            this.oferta = data.oferta;
    }
    ngOnInit() {
        this.crearFormularioCarrera();
        this.insertarInformacionDeLaCarreraEnFormulario();
        this.getCarreras();
    }

    crearFiltroCarreras() {

        this.filtroCarreras = this.form.controls['carrera'].valueChanges.pipe(
            startWith(''),
            map(val => this.filtrarCarrera(val))
          );
    }

    filtrarCarrera(val: string): Carrera[] {
        return this.carreras.filter(option => {
          return option.descripcion.toLowerCase().match(val.toLowerCase());
        });
      }

    getCarreras() {
        this.restService.getCarreras().subscribe(carreras => {
          this.carreras = carreras;
          this.crearFiltroCarreras();
        },
        (err) => {
            this.utilesService.mostrarMensajeDeError(err);
        });
      }


    crearFormularioCarrera() {
        this.form = this.fb.group({
            nombre: ['', Validators.required],
            descripcion: ['', Validators.required],
            carrera: ['', Validators.required]
        });
    }

    insertarInformacionDeLaCarreraEnFormulario() {
        if (this.oferta != null) {
            this.form.setValue({
                'nombre': this.oferta.nombre,
                'descripcion': this.oferta.descripcion,
                'carrera': this.oferta.carrera.descripcion
              });
            this.checked = this.oferta.habilitada;
            this.carreraParaOferta = this.oferta.carrera;
        }
    }


    guardar() {
        if (this.form.valid) {
            const { nombre, descripcion} = this.form.value;
            const oferta = new OfertaAcademica(nombre, descripcion, this.checked, this.carreraParaOferta);
            this.dialogRef.close(oferta);
        }
    }

    cerrar() {
        this.dialogRef.close();
    }

    carreraSeleccionada(carrera) {
        this.carreraParaOferta = carrera;
    }
}
