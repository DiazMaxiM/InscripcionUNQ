export class Incidencia {
	id?: string;
	tipoIncidencia?: string;
	descripcion?: string;
	tipoEstadoIncidencia?: any;

	constructor(
		tipoIncidencia?: string,
		descripcion?: string,
		tipoEstadoIncidencia?: string,
		id?: string
	) {
		this.tipoIncidencia = tipoIncidencia;
		this.descripcion = descripcion;
		this.tipoEstadoIncidencia = tipoEstadoIncidencia;
		this.id = id;
	}
}