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
import { Periodo } from '../periodos/periodo.model';
import { AppRutas } from '../app-rutas.model';

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
    periodos: Periodo[];
    filtroPeriodos: Observable<Periodo[]>;

    carreraParaOferta: Carrera;
    periodoParaOferta: Periodo;


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
        this.getPeriodos();
    }

    crearFiltroCarreras() {
        this.filtroCarreras = this.form.controls['carrera'].valueChanges.pipe(
            startWith(''),
            map(val => this.filtrarCarrera(val))
          );
    }

    crearFiltroPeriodos() {

        this.filtroPeriodos = this.form.controls['periodo'].valueChanges.pipe(
            startWith(''),
            map(val => this.filtrarPeriodo(val))
          );
    }

    filtrarPeriodo(val: string): Periodo[] {
        return this.periodos.filter(option => {
          return option.codigo.toLowerCase().match(val.toLowerCase());
        });
      }



    filtrarCarrera(val: string): Carrera[] {
        return this.carreras.filter(option => {
          return option.descripcion.toLowerCase().match(val.toLowerCase());
        });
      }

    irATarerasUsuario(mensaje){
        this.cerrar();
        this.utilesService.mostrarMensajeYRedireccionar(mensaje, AppRutas.TAREAS_USUARIO);
    }
    guardarCarreras(carreras: Carrera[]) {
       if (carreras.length == 0 ) {
         const mensaje = 'No se encontraron carreras para la oferta';
         this.irATarerasUsuario(mensaje);
       }
       this.carreras = carreras;
       this.crearFiltroCarreras();
    }

    getCarreras() {
        this.restService.getCarreras().subscribe(carreras => {
          this.guardarCarreras(carreras);
        },
        (err) => {
            this.utilesService.mostrarMensajeDeError(err);
        });
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
          this.irATarerasUsuario(mensaje);
        }
        this.periodos = periodos;
        this.crearFiltroPeriodos();
     }


    crearFormularioCarrera() {
        this.form = this.fb.group({
            nombre: ['', Validators.required],
            descripcion: ['', Validators.required],
            carrera: ['', Validators.required],
            periodo: ['', Validators.required]
        });
    }

    insertarInformacionDeLaCarreraEnFormulario() {
        if (this.oferta != null) {
            this.form.setValue({
                'nombre': this.oferta.nombre,
                'descripcion': this.oferta.descripcion,
                'carrera': this.oferta.carrera.descripcion,
                'periodo': this.oferta.periodo.codigo
              });
            this.checked = this.oferta.habilitada;
            this.carreraParaOferta = this.oferta.carrera;
        }
    }


    guardar() {
        if (this.form.valid) {
            const { nombre, descripcion} = this.form.value;
            const oferta = new OfertaAcademica(nombre, descripcion, this.checked, this.carreraParaOferta, this.periodoParaOferta);
            this.dialogRef.close(oferta);
        }
    }

    cerrar() {
        this.dialogRef.close();
    }

    carreraSeleccionada(carrera) {
        this.carreraParaOferta = carrera;
    }

    periodoSeleccionado(periodo) {
        this.periodoParaOferta = periodo;
    }
}
