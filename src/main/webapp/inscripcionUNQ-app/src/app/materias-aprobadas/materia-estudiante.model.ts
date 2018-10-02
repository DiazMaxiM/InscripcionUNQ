export class  MateriaEstudiante {
  id?: number;
  codigo?: string;
  nombre?: string;
  aprobada?: boolean;
  checked?: boolean;
  nombreComision?: string;
  comisionRegistrado?: any;

  constructor(
    id?: number,
    codigo?: string,
    nombre?: string,
    aprobada?: boolean,
    checked?: boolean,
    nombreComision?: string,
    comisionRegistrado?: any

  ) {
    this.id = id;
    this.codigo = codigo;
    this.nombre = nombre;
    this.aprobada = aprobada;
    this.checked = checked;
    this.nombreComision = nombreComision;
    this.comisionRegistrado = comisionRegistrado;
  }
}
