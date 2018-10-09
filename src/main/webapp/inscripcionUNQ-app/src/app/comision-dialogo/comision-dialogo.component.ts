import {Component, Inject, OnInit, ViewEncapsulation} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {FormBuilder, Validators, FormGroup, FormControl} from '@angular/forms';
import { Comision } from '../comisiones-de-oferta/comision.model';
import { UtilesService } from '../utiles.service';
import { DataDialogo } from './data-dialogo.model';
import { Materia } from '../materias/materia.model';
import { Observable } from 'rxjs';
import { startWith, map } from 'rxjs/operators';
import { RestService } from '../rest.service';
import { Periodo } from '../periodos/periodo.model';
import { AppRutas } from '../app-rutas.model';
import { Horario } from '../seleccion-de-comision-dialogo/horario.model';
import { HorarioComision } from './horario-comision.model';

@Component({
    selector: 'app-comision-dialogo',
    templateUrl: './comision-dialogo.component.html',
    styleUrls: ['../dialogo-abm.component.css']
})

export class ComisionDialogoComponent implements OnInit {

    form: FormGroup;
    formHorario: FormGroup;
    comision: Comision;
    materias: Materia[];
    filtroMaterias: Observable<Materia[]>;
    materiaParaComision: Materia;
    mostrarformularioParaHorarios = false;
    dias;
    periodos: Periodo[];
    filtroPeriodos: Observable<Periodo[]>;
    periodoActual;
    horarios = [];

    constructor(
        private fb: FormBuilder,
        private utilesService: UtilesService,
        private dialogRef: MatDialogRef<ComisionDialogoComponent>,
        private restService: RestService,
        @Inject(MAT_DIALOG_DATA) public data: DataDialogo ) {
                this.comision = data.comision;
    }
    ngOnInit() {
        this.crearFormularioComision();
        this.insertarInformacionDeComisioEnFormulario();
        this.getMaterias();
        this.crearFormularioParaHorario();
        this.getDias();
        this.getPeriodos();

    }

    getDias() {
        this.restService.getDias().subscribe(dias => {
            this.dias = dias;
        })
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

        this.restService.getMaterias().subscribe(materias => {
          this.materias = materias;
          this.crearFiltroMaterias();
        },
        (err) => {
            this.utilesService.mostrarMensajeDeError(err);
        });
      }

    crearFormularioParaHorario() {
        this.formHorario = this.fb.group({
            dia: ['', Validators.required],
            horarioComienzo: ['', Validators.required],
            duracion: ['', Validators.required]
        });

    }
    crearFormularioComision() {
        this.form = this.fb.group({
            nombre: ['', Validators.required],
            cupo: ['', Validators.required],
            materia: ['', Validators.required],
            periodo: ['', Validators.required],
        });
    }

    insertarInformacionDeComisioEnFormulario() {
        if (this.comision != null) {
            this.form.setValue({
                'nombre': this.comision.nombre,
                'cupo': this.comision.cupo,
                'materia': this.comision.materia.nombre,
                'periodo': this.comision.periodo.codigo
              });

        }
    }

    guardar() {
        if (this.form.valid && this.horarios.length > 0) {
            const { nombre, cupo} = this.form.value;
            const comision = new Comision();
            comision.nombre = nombre;
            comision.cupo = cupo;
            comision.materia = this.materiaParaComision;
            comision.periodo = this.periodoActual;
            comision.horarioJson = this.horarios;
            this.dialogRef.close(comision);
        }
    }

    cerrar() {
        this.dialogRef.close();
    }

    mostarFormularioHorarios() {
       this.mostrarformularioParaHorarios = true;
    }

    guardarHorario() {
        if (this.formHorario.valid) {
            const { dia, horarioComienzo, duracion} = this.formHorario.value;
            const horario = new HorarioComision(dia, horarioComienzo, duracion);
            this.horarios.push(horario);
            this.mostrarformularioParaHorarios = false;
        }
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
          const mensaje = 'No se encontraron períodos para la comisión';
          this.irATarerasUsuario(mensaje);
        }
        this.periodos = periodos;
        this.crearFiltroPeriodos();
     }

     irATarerasUsuario(mensaje) {
        this.cerrar();
        this.utilesService.mostrarMensajeYRedireccionar(mensaje, AppRutas.TAREAS_USUARIO);
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

    periodoSeleccionado(periodo) {
      this.periodoActual = periodo;
    }

    materiaSeleccionada(materia) {
        this.materiaParaComision = materia;
    }
}
