export class Carrera {
    id?: number;
    codigo?: number;
    descripcion?: string;
    habilitada?: boolean;

  constructor(
    codigo?: number,
    descripcion?: string,
    habilitada?: boolean,
    id?: number
  ) {
    this.codigo = codigo;
    this.descripcion = descripcion;
    this.habilitada = habilitada;
    this.id = id;
  }
}
