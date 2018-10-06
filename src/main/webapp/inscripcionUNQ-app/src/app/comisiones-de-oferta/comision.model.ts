export class Comision {
    idComision?: number;
    nombre?: string;
    nombreMateria?: string;
    horarios = [];
  
    constructor(
      idComision?: number,
      nombre?: string,
      nombreMateria?: string,
      horarios?: any
    ) {
      this.idComision = idComision;
      this.nombre = nombre;
      this.nombreMateria = nombreMateria;
      this.horarios = horarios;
    }
  
    agregarHorario(horario) {
      this.horarios.push(horario);
    }
  }