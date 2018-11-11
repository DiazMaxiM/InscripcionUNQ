import { Materia } from '../materias/materia.model';

export class Equivalencia {
	id?: string;
	materiaOrigen?: Materia;
	materiaDestino?: Materia;

	constructor(
		materiaOrigen?: Materia,
		materiaDestino?: Materia,
		id?: string
	) {
		this.materiaOrigen = materiaOrigen;
		this.materiaDestino = materiaDestino;
		this.id = id;
	}
}