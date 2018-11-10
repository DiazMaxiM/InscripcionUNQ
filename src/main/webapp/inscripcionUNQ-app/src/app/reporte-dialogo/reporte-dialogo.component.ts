import { Component, OnInit, Inject } from "@angular/core";
import { MatDialogRef, MAT_DIALOG_DATA } from "@angular/material";
import { FormBuilder, Validators, FormGroup } from "@angular/forms";
import { UtilesService } from "../utiles.service";
import { RestService } from "../rest.service";
import { DataDialogo } from "./data-dialogo.model";
import { Encuesta } from "../encuesta-dialogo/encuesta.model";
import { httpFactory } from "@angular/http/src/http_module";

@Component({
  selector: "app-reporte-dialogo",
  templateUrl: "./reporte-dialogo.component.html",
  styleUrls: ["../dialogo-abm.component.css"]
})
export class ReporteDialogoComponent implements OnInit {
  encuesta: Encuesta;
  form: FormGroup;
  tipoReporte: String[] = [];

  constructor(
    private fb: FormBuilder,
    private utilesService: UtilesService,
    private restService: RestService,
    private dialogRef: MatDialogRef<ReporteDialogoComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DataDialogo
  ) {
    this.encuesta = this.data.encuesta;
  }

  ngOnInit() {
    this.crearFormularioReporte();
    this.getTipoReporte();
  }

  getTipoReporte() {
    if (this.tipoReporte.length == 0) {
      this.restService.getTiposReporte().subscribe(reportes => {
        this.tipoReporte = reportes;
      });
    }
  }

  crearFormularioReporte() {
    this.form = this.fb.group({
      tipoReporte: ["", Validators.required]
    });
  }

  cerrar() {
    this.dialogRef.close();
  }

  descargarReporte() {
		const idEncuesta = this.encuesta.id;
		if (this.form.valid) {
			const { tipoReporte } = this.form.value;
			this.restService.getReporte(idEncuesta,tipoReporte).subscribe(data => {
				this.descargar(data);
				this.cerrar();
			},
			(err) => {
				this.utilesService.mostrarMensaje('Sin datos para generar el reporte');
			});
			
		} else {
			this.utilesService.validateAllFormFields(this.form);
		}
  }

  descargar(excel) {		
    const nombreDeArchivo = this.encuesta.nombre + ".xls";
    if (navigator.msSaveBlob) {
      // IE 10+
      navigator.msSaveBlob(excel, nombreDeArchivo);
    } else {
      const link = document.createElement("a");
      if (link.download !== undefined) {
        const url = URL.createObjectURL(excel);
        link.setAttribute("href", url);
        link.setAttribute("download", nombreDeArchivo);
        link.style.visibility = "hidden";
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
      } else {
        // html5 download no soportado
        const mensaje = "No se puede descargar el reporte desde tu navegador";
        this.utilesService.mostrarMensajeYSalir(mensaje);
      }
    }
  }
}