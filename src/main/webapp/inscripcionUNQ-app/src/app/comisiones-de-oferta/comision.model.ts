export class Comision {
    idComision?: number;
    nombre?: string;
    nombreMateria?: string;
    horarios = [];
    cupo?: string;


    constructor(
      idComision?: number,
      nombre?: string,
      nombreMateria?: string,
      horarios?: any,
      cupo?: string,
    ) {
      this.idComision = idComision;
      this.nombre = nombre;
      this.nombreMateria = nombreMateria;
      this.horarios = horarios;
      this.cupo = cupo;
    }

    agregarHorario(horario) {
      this.horarios.push(horario);
    }
  }