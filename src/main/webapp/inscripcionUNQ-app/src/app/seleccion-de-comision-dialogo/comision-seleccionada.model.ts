export class ComisionSeleccionada {

  idComision?: number;
  nombreDeLaComision?: string;
  horariosSeleccionados = [];
  idMateria?: number;

  constructor(
    idComision?: number,
    nombreDeLaComision?: string,
    idMateria?: number,

  ) {

    this.idComision = idComision;
    this.nombreDeLaComision = nombreDeLaComision;
    this.idMateria = idMateria;
  }

  agregarHorario(horario) {
    this.horariosSeleccionados.push(horario);
  }
}
