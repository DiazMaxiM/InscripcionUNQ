export class Periodo {
	id?: string;
	codigo?: string;
	anho?: number;
	numero?: number;
	tipoPeriodo?: string;

	constructor(
		anho?: number,
		numero?: number,
		tipoPeriodo?: string,

	) {
		this.anho = anho;
		this.numero = numero;
		this.tipoPeriodo = tipoPeriodo;
	}
}