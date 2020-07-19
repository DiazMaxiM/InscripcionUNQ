export class Materia {
	id?: number;
	codigo?: number;
	nombre?: string;
	horas?: number;
	estado: boolean;
	carreras?: any;
	creditos?: number;
	prerrequisitos?: any;

	constructor(
		codigo?: number,
		nombre?: string,
		carreras?: any,
		estado?: boolean,
		horas?: number,
		creditos?: number,
		prerrequisitos?: any,
		id?: number
	) {
		this.codigo = codigo;
		this.nombre = nombre;
		this.carreras = carreras;
		this.estado = estado;
		this.horas = horas;
		this.creditos = creditos;
		this.prerrequisitos = prerrequisitos;
		this.id = id;
	}
}