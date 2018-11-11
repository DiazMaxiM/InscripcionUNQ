export class Fecha {
  dia?: number;
  mes?: number;
  anho?: number;
  horario?;

  constructor(dia?: number, mes?: number, anho?: number, horario?) {
    this.dia = dia;
    this.mes = mes;
    this.anho = anho;
    this.horario = horario;
  }
}