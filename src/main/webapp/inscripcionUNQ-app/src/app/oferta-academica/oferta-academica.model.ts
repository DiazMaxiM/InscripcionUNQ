import { Carrera } from '../carreras/carrera.model';

export class OfertaAcademica {
    id?: number;
    nombre?: string;
    descripcion?: string;
    habilitada?: boolean;
    carrera?: Carrera;

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