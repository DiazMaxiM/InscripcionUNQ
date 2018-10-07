export class Incidencia {
    id?: string;
    tipoIncidencia?: string;
    descripcion?: string;

  constructor(
    tipoIncidencia?: string,
    descripcion?: string,
    id?: string
  ) {
    this.tipoIncidencia = tipoIncidencia;
    this.descripcion = descripcion;
    this.id = id;
  }
}