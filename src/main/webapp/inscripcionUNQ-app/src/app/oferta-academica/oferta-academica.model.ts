import { Carrera } from '../carreras/carrera.model';
import { Periodo } from '../periodos/periodo.model';

export class OfertaAcademica {
    id?: string;
    nombre?: string;
    descripcion?: string;
    habilitada?: boolean;
    carrera?: Carrera;
    nroComisionesCreadas?: number;
    periodo?: Periodo;

  constructor(
    nombre?: string,
    descripcion?: string,
    habilitada?: boolean,
    carrera?: Carrera,
    periodo?: Periodo
  ) {
    this.nombre = nombre;
    this.descripcion = descripcion;
    this.habilitada = habilitada;
    this.carrera = carrera;
    this.periodo = periodo;
  }
}
