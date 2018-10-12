import {Component, Inject, OnInit, ViewEncapsulation} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef, MatOptionSelectionChange} from '@angular/material';
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
import { HorarioComision } from './horario-comision.model';
import { AppMensajes } from '../app-mensajes.model';

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
    mostrarformularioParaHorarios = false;
    mostrarTablaHorarios = false;
    dias;
    periodos: Periodo[];
    filtroPeriodos: Observable<Periodo[]>;
    horarios = [];
    horarioAEditar: HorarioComision;

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
        this.insertarInformacionDeComisionEnFormularioYCrearTablaDeHorarios();
        this.getMaterias();
        this.crearFormularioParaHorario();
        this.getDias();
        this.getPeriodos();

    }

    getDias() {
        this.restService.getDias().subscribe(dias => {
            this.dias = dias;
        });
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

    insertarInformacionDeComisionEnFormularioYCrearTablaDeHorarios() {
        if (this.comision != null) {
            this.form.setValue({
                'nombre': this.comision.nombre,
                'cupo': this.comision.cupo,
                'materia': this.comision.materia.nombre,
                'periodo': this.comision.periodo.codigo
              });

           this.crearTablaConHorarios(this.comision.horarioJson);
        }
    }

    crearTablaConHorarios(horarioJson: HorarioComision[]) {
        for(const horario of horarioJson) {
            this.agregarHorario(horario.dia, horario.horaComienzo, horario.duracion);
            this.mostrarHorariosSeleccionados();
        }
    }

    guardar() {
        if (this.form.valid) {
           this.armarComision();
        } else {
            this.utilesService.validateAllFormFields(this.form);
        }
    }

    armarComision() {
        const { nombre, cupo, materia, periodo} = this.form.value;
        if (this.hayMateriaValida(materia) && this.hayPeriodoValido(periodo) && this.hayHorariosSeleccionados()) {
            const comision = new Comision();
            comision.nombre = nombre;
            comision.cupo = cupo;
            comision.materia = this.utilesService.obtenerMateria(this.materias, materia);
            comision.periodo = this.utilesService.obtenerPeriodo(this.periodos,periodo);
            comision.horarioJson = this.horarios;
            this.dialogRef.close(comision);
        }
    }

    hayMateriaValida(materia) {
        return this.utilesService.materiaValida(this.materias,materia)
    }

    hayPeriodoValido(periodo) {
        return this.utilesService.periodoValido(this.periodos,periodo);
    }

    hayHorariosSeleccionados(){
        if(this.horarios.length == 0) {
            this.utilesService.mostrarMensaje(AppMensajes.NO_HAY_HORARIOS_CARGADOS);
            return false;
        } else {
            return true;
        }
    }

    cerrar() {
        this.dialogRef.close();
    }

    mostarFormularioHorarios() {
       this.mostrarformularioParaHorarios = true;
       this.mostrarTablaHorarios = false;
    }

    mostrarHorariosSeleccionados() {
        if (this.horarios.length > 0) {
            this.mostrarTablaHorarios = true;
        } else {
            this.mostrarTablaHorarios = false;
        }
        this.mostrarformularioParaHorarios = false;
    }

    guardarHorario() {
        if (this.formHorario.valid) {
            const { dia, horarioComienzo, duracion} = this.formHorario.value;
            this.agregarHorario(dia, horarioComienzo, duracion);
            this.mostrarHorariosSeleccionados();
            this.formHorario.reset();
        } else {
            this.utilesService.validateAllFormFields(this.formHorario);
        }
    }

    agregarHorario(dia, horarioComienzo, duracion) {
        const horario = new HorarioComision(dia, horarioComienzo, duracion);
        if (this.horarioAEditar != null) {
            this.horarios[this.horarios.indexOf(this.horarioAEditar)] = horario;
            this.horarioAEditar = null;
        } else  {
            this.horarios.push(horario);
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

    editarHorarip(horario: HorarioComision){
        this.horarioAEditar = horario;
        this.formHorario.setValue({
            'dia': horario.dia,
            'duracion': horario.duracion,
            'horarioComienzo': horario.horaComienzo,
          });
        this.mostarFormularioHorarios();
    }


    cancelarHorario() {
        this.formHorario.reset();
        if (this.horarios.length > 0) {
            this.mostrarHorariosSeleccionados();
        } else{
            this.mostrarformularioParaHorarios = false;
        }
    }

    eliminarHorario(horario) {
        const index = this.horarios.indexOf(horario);
        this.horarios.splice(index, 1);
        this.mostrarHorariosSeleccionados();

    }
}
