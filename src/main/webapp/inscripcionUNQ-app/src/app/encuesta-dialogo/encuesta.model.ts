import { Periodo } from '../periodos/periodo.model';
import { Fecha } from './fecha.model';
import { OfertaAcademica } from '../oferta-academica/oferta-academica.model';

export class Encuesta {

	nombre?: string;
	fechaComienzo?: Fecha;
	fechaFin?: Fecha;
	periodo: Periodo;
	id?: number;
	ofertasAcademicas: OfertaAcademica[];

	constructor(
		nombre?: string,
		fechaComienzo?: Fecha,
		fechaFin?: Fecha,
		periodo?,
		id?: number
	) {
		this.nombre = nombre;
		this.fechaComienzo = fechaComienzo;
		this.fechaFin = fechaFin;
		this.periodo = periodo;
		this.id = id;
	}
}