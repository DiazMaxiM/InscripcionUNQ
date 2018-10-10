export class HorarioComision {
    id?: number;
    dia: string;
    horaComienzo;
    duracion: number;

    constructor(
      dia: string,
      horario,
      duracion: number,
      id?: number
    ) {
      this.dia = dia;
      this.horaComienzo = horario;
      this.duracion = duracion;
      this.id = id;
    }
  }