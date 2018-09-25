export class Carrera {

    codigo: number;
    descripcion: string;
    estado: boolean;

  constructor(

    codigo: number,
    descripcion: string,
    estado: boolean,
  ) {
    this.codigo = codigo;
    this.descripcion = descripcion;
    this.estado = estado;
  }
}
