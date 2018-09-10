export class Horario {
  dia: string;
  horarioDeComienzo: Date;
  horarioDeFinalizacion: Date;

  constructor(
    dia: string,
    horarioDeComienzo: Date,
    horarioDeFinalizacion: Date
  ) {
    this.dia = dia;
    this.horarioDeComienzo = horarioDeComienzo;
    this.horarioDeFinalizacion = horarioDeFinalizacion;
  }
}
