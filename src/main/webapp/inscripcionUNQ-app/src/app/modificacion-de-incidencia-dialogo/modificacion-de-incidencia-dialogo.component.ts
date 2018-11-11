import { Component, Inject, OnInit } from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { MatDialogRef, MAT_DIALOG_DATA } from "@angular/material";
import { RestService } from "../rest.service";
import { UtilesService } from "../utiles.service";
import { DataDialogo } from "./data-dialogo.model";
import { IncidenciaEstado } from "../incidencias/incidencia-estado.model";
import { startWith, map } from "rxjs/operators";
import { Observable } from "rxjs";

@Component({
  selector: "app-modificacion-de-incidencia-dialogo",
  templateUrl: "./modificacion-de-incidencia-dialogo.component.html",
  styleUrls: ["../dialogo-abm.component.css"]
})
export class ModificacionDeIncidenciaDialogoComponent implements OnInit {
  incidencia: IncidenciaEstado;
  tiposEstadoIncidencia: String[] = [];
  filtroTipoEstadoIncidencia: Observable<any[]>;
  form: FormGroup;

  constructor(
    private fb: FormBuilder,
    private utilesService: UtilesService,
    private restService: RestService,
    private dialogRef: MatDialogRef<ModificacionDeIncidenciaDialogoComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DataDialogo
  ) {
    this.incidencia = this.data.incidencia;
  }

  ngOnInit() {
    this.crearFormularioIncidencia();
    this.insertarInformacionDeLaIncidenciaEnFormulario();
  }

  crearFormularioIncidencia() {
    this.form = this.fb.group({
      tipoIncidencia: ["", Validators.required],
      descripcion: ["", Validators.required],
      tipoEstadoIncidencia: ["", Validators.required]
    });
    this.getTipoEstadoIncidencia();
  }

  insertarInformacionDeLaIncidenciaEnFormulario() {
    if (this.incidencia != null) {
      this.form.setValue({
        tipoIncidencia: this.incidencia.tipoIncidencia.descripcion,
        descripcion: this.incidencia.descripcion,
        tipoEstadoIncidencia: this.incidencia.tipoEstadoIncidencia
      });
    }
  }

  guardar() {
    if (this.form.valid) {
      this.armarIncidencia();
    } else {
      this.utilesService.validateAllFormFields(this.form);
    }
  }

  crearFiltroTipoEstadoIncidencia() {
    this.filtroTipoEstadoIncidencia = this.form.controls[
      "tipoEstadoIncidencia"
    ].valueChanges.pipe(
      startWith(""),
      map(val => this.filtrarTipoEstadoIncidencia(val))
    );
  }

  filtrarTipoEstadoIncidencia(val: string): String[] {
    return this.tiposEstadoIncidencia.filter(option => {
      return option.toLowerCase().match(val.toLowerCase());
    });
  }

  armarIncidencia() {
    const {
      tipoIncidencia,
      descripcion,
      tipoEstadoIncidencia
    } = this.form.value;
    this.dialogRef.close(tipoEstadoIncidencia);
  }

  cerrar() {
    this.dialogRef.close();
  }

  getTipoEstadoIncidencia() {
    if (this.tiposEstadoIncidencia.length == 0) {
      this.restService.getTipoEstadoIncidencias().subscribe(incidencias => {
        this.tiposEstadoIncidencia = incidencias;
        this.crearFiltroTipoEstadoIncidencia();
      });
    }
  }
}