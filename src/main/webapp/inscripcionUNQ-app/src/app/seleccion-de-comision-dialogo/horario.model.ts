export class Horario {
	dia: string;
	horarioDeComienzo: Date;
	horarioDeFinalizacion: Date;
	duracion?; number;

	constructor(
		dia: string,
		horarioDeComienzo: Date,
		horarioDeFinalizacion: Date,
		duracion?: number
	) {
		this.dia = dia;
		this.horarioDeComienzo = horarioDeComienzo;
		this.horarioDeFinalizacion = horarioDeFinalizacion;
		this.duracion = duracion;
	}
}