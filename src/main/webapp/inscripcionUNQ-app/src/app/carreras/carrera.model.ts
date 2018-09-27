export class Carrera {
    id?: number;
    codigo?: number;
    descripcion?: string;
    estado?: boolean;

  constructor(
    codigo?: number,
    descripcion?: string,
    estado?: boolean,
    id?: number
  ) {
    this.codigo = codigo;
    this.descripcion = descripcion;
    this.estado = estado;
    this.id = id;
  }
}
