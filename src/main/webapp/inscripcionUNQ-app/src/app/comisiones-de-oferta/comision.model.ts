import { Periodo } from '../periodos/periodo.model';
import { Materia } from '../materias/materia.model';

export class Comision {
    id?: number;
    nombre?: string;
    horarioJson = [];
    cupo?: string;
    periodo?: Periodo;
    materia?: Materia;


    constructor(
      nombre?: string,
      cupo?: string,
      periodo?: Periodo,
      materia?: Materia,
      idComision?: number,
    ) {
      this.id = idComision;
      this.nombre = nombre;
      this.cupo = cupo;
      this.periodo = periodo;
      this.materia = materia;
    }

    agregarHorario(horario) {
      this.horarioJson.push(horario);
    }
  }