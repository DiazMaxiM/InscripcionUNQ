export class IncidenciaEstado {
    id?: String;
    tipoIncidencia?: any;
    descripcion?: string;
    tipoEstadoIncidencia?: any;
  
  constructor(
    tipoIncidencia?: any,
    descripcion?: string,
    tipoEstadoIncidencia?: string,
    id?: String
  ) {
    this.tipoIncidencia = tipoIncidencia;
    this.descripcion = descripcion;
    this.tipoEstadoIncidencia = tipoEstadoIncidencia; 
    this.id = id;
  }
}