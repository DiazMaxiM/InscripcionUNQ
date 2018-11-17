export class Incidencia {
	id?: string;
	tipoIncidencia?: string;
	descripcion?: string;
	tipoEstadoIncidencia?: any;
	emailDelReportante?: string;

	constructor(
		tipoIncidencia?: string,
		descripcion?: string,
		emailDelReportante?: string,
		tipoEstadoIncidencia?: string,
		id?: string
	) {
		this.tipoIncidencia = tipoIncidencia;
		this.descripcion = descripcion;
		this.tipoEstadoIncidencia = tipoEstadoIncidencia;
		this.emailDelReportante = emailDelReportante;
		this.id = id;
	}
}