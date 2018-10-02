export class Materia {
    id?: number;
    codigo?: number;
    nombre?: string;
    horas?: number;
    estado: boolean;
    carreras?: any;
  
  constructor(
    codigo?: number,
    nombre?: string,
    carreras?: any,
    estado?: boolean,
    horas?: number,
    id?: number
  ) {
    this.codigo = codigo;
    this.nombre = nombre;
    this.carreras = carreras;
    this.estado = estado; 
    this.horas = horas;
    this.id = id;
  }
}