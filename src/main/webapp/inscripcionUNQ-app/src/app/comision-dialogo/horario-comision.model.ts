export class HorarioComision {
    dia: string;
    horaComienzo;
    duracion: number;

    constructor(
      dia: string,
      horario,
      duracion: number
    ) {
      this.dia = dia;
      this.horaComienzo = horario;
      this.duracion = duracion;
    }
  }