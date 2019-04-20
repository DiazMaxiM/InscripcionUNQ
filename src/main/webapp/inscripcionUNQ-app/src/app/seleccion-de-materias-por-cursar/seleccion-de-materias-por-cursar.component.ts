import { Component, OnInit } from "@angular/core";
import { RestService } from "../rest.service";
import { ComisionSeleccionada } from "../seleccion-de-comision-dialogo/comision-seleccionada.model";
import { MateriaEstudiante } from "../materias-aprobadas/materia-estudiante.model";
import { RegistroDeComisionesSeleccionadasService } from "./registro-de-comisiones-seleccionadas.service";
import { UtilesService } from "../utiles.service";
import { Comision } from "../comisiones-de-oferta/comision.model";
import { DialogosService } from "../dialogos.service";

@Component({
  selector: "app-seleccion-de-materias-por-cursar",
  templateUrl: "./seleccion-de-materias-por-cursar.component.html",
  styleUrls: ["./seleccion-de-materias-por-cursar.component.css"]
})
export class SeleccionDeMateriasPorCursarComponent implements OnInit {
  materiasDisponibles: MateriaEstudiante[] = [];
  materiasDisponiblesActivas: MateriaEstudiante[] = [];
  comisionesSeleccionadas: ComisionSeleccionada[] = [];
  idEstudiante: string;
  materiaBuscada;

  constructor(
    private restService: RestService,
    private registroComisionesService: RegistroDeComisionesSeleccionadasService,
		private utilesService: UtilesService,
    private dialogosService: DialogosService) { }

  ngOnInit() {
    this.idEstudiante = localStorage.getItem("idEstudiante");
    this.limpiarInformacionComisionesSeleccionadas();
    this.obtenerMateriasDisponibles();
  }

  obtenerMateriasDisponibles() {
    this.restService
      .obtenerMateriasDisponibles(this.idEstudiante)
      .subscribe(materiasDisponibles => {
        this.materiasDisponibles = materiasDisponibles;
        this.materiasDisponiblesActivas = this.materiasDisponibles;
        this.marcarMateriasAnteriormenteSeleccionadas();
      });
  }


  actualizarMateiasSeleccionadas() {
    this.materiasDisponiblesActivas = this.materiasDisponibles.slice(
      0,
      this.materiasDisponibles.length
    );
  }

  materiaSeleccionada(materia, checkbox) {
    if (checkbox.checked) {
      this.agregarComisionSeleccionada(materia);
    } else {
      this.eliminarComisionSeleccionada(materia.id);
      this.deseleccionarMateria(materia);
    }
  }

  agregarComisionSeleccionada(materia) {
    const comisiones: any[] = materia.comisionesJson;
    if (comisiones.length > 1) {
      this.abrirDialogoParaSeleccionarComision(materia);
    } else {
      const comision = comisiones[0];
      const registro = this.registroComisionesService.crearRegistroDeComisionSeleccionada(
        materia.id,
        comision
      );
      this.guardarRegistro(materia, registro);
    }
  }

  mostrarInformacionDelaComisionSeleccionada(
    materia,
    comisionSeleccionada: ComisionSeleccionada
  ) {
    const materiaActualizada = this.materiaActualizada(
      materia,
      comisionSeleccionada.nombreDeLaComision,
      comisionSeleccionada.horariosSeleccionados,
      true
    );
    this.materiasDisponibles[
      this.materiasDisponibles.indexOf(materia)
    ] = materiaActualizada;
    this.actualizarMateiasSeleccionadas();
  }

  materiaActualizada(
    oldSubject,
    nombreComision,
    horariosSeleccionados,
    checked
  ) {
    return {
      id: oldSubject.id,
      codigo: oldSubject.codigo,
      nombre: oldSubject.nombre,
      aprobada: oldSubject.aprobada,
      checked: checked,
      nombreComision: nombreComision,
      horariosSeleccionados: horariosSeleccionados,
      comisionesJson: oldSubject.comisionesJson
    };
  }

  abrirDialogoParaSeleccionarComision(materia) {
    this.dialogosService.abrirDialogoParaSeleccionarComision(materia).subscribe(registro => {
      this.guardarRegistro(materia, registro);
		});

  }

  deseleccionarMateria(materia) {
    const materiaActualizada = this.materiaActualizada(materia, "", [], false);
    this.materiasDisponibles[
      this.materiasDisponibles.indexOf(materia)
    ] = materiaActualizada;
    this.actualizarMateiasSeleccionadas();
  }

  guardarRegistro(materia, registro: ComisionSeleccionada) {
    if (registro != null && registro.horariosSeleccionados.length > 0) {
      this.mostrarInformacionDelaComisionSeleccionada(materia, registro);
      this.comisionesSeleccionadas.push(registro);
    } else {
      if (registro != null && registro.horariosSeleccionados.length == 0) {
        this.utilesService.mostrarMensaje(
          "No se pueden cursar materias en horarios que se superponen"
        );
      }
      this.deseleccionarMateria(materia);
    }
  }

  eliminarComisionSeleccionada(idMateria) {
    const registro = this.comisionesSeleccionadas.find(function(
      registroDeComision
    ) {
      return registroDeComision.idMateria == idMateria;
    });
    const index = this.comisionesSeleccionadas.indexOf(registro);
    this.comisionesSeleccionadas.splice(index, 1);
    this.registroComisionesService.eliminarHorarios(
      registro.horariosSeleccionados
    );
  }

  finalizarEncuesta() {
    if (this.comisionesSeleccionadas.length == 0) {
      this.utilesService.mostrarMensaje("No ha seleccionado ninguna materia");
    } else {
      this.enviarComisionesSeleccionadas();
    }
  }

  enviarComisionesSeleccionadas() {
    const comisiones = [];
    for (const comision of this.comisionesSeleccionadas) {
      const comisionSeleccionada = new Comision();
      comisionSeleccionada.id = comision.idComision;
      comisiones.push(comisionSeleccionada);
    }
    this.utilesService.activarDialogoCargando();
    this.restService
      .enviarComisionesSeleccionadas(this.idEstudiante, comisiones)
      .subscribe(
        data => {
          this.utilesService.desactivarDialogoCargandoYRedireccionar(
            "encuesta-finalizada"
          );
        },
        err => {
          this.utilesService.desactivarDialogoCargando();
          this.utilesService.mostrarMensajeDeError(err);
        }
      );
  }

  marcarMateriasAnteriormenteSeleccionadas() {
    for (const materia of this.materiasDisponibles) {
      if (materia.comisionRegistrado != null) {
        const comisionSeleccionada = this.registroComisionesService.crearRegistroDeComisionSeleccionada(
          materia.id,
          materia.comisionRegistrado
        );
        this.guardarRegistro(materia, comisionSeleccionada);
      }
    }
  }

  limpiarInformacionComisionesSeleccionadas() {
    this.comisionesSeleccionadas = [];
    this.registroComisionesService.limpiarHorarios();
  }

  volver(){
		this.utilesService.volver();
	}
}
