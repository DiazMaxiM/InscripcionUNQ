export class Carrera {
	id?: string;
	codigo?: number;
	descripcion?: string;
	habilitada?: boolean;

	constructor(
		codigo?: number,
		descripcion?: string,
		habilitada?: boolean,
		id?: string
	) {
		this.codigo = codigo;
		this.descripcion = descripcion;
		this.habilitada = habilitada;
		this.id = id;
	}
}