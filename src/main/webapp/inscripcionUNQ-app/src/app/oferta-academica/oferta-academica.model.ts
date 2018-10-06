import { Carrera } from '../carreras/carrera.model';

export class OfertaAcademica {
    id?: string;
    nombre?: string;
    descripcion?: string;
    habilitada?: boolean;
    carrera?: Carrera;
    nroComisionesCreadas?: number;

  constructor(
    nombre?: string,
    descripcion?: string,
    habilitada?: boolean,
    carrera?: Carrera
  ) {
    this.nombre = nombre;
    this.descripcion = descripcion;
    this.habilitada = habilitada;
    this.carrera = carrera;
  }
}