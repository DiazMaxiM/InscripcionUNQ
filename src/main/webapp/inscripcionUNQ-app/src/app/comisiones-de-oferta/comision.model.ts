export class Comision {
    idComision?: number;
    nombre?: string;
    horarios = [];
    idMateria?: number;
  
    constructor(
      idComision?: number,
      nombre?: string,
      idMateria?: number,
    ) {
      this.idComision = idComision;
      this.nombre = nombre;
      this.idMateria = idMateria;
    }
  
    agregarHorario(horario) {
      this.horarios.push(horario);
    }
  }