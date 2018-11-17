export class IncidenciaEstado {
	id?: String;
	tipoIncidencia?: any;
	descripcion?: string;
	emailDelReportante?: string;
	tipoEstadoIncidencia?: any;

	constructor(
		tipoIncidencia?: any,
		descripcion?: string,
		tipoEstadoIncidencia?: string,
		emailDelReportante?: string,
		id?: String
	) {
		this.tipoIncidencia = tipoIncidencia;
		this.descripcion = descripcion;
		this.tipoEstadoIncidencia = tipoEstadoIncidencia;
		this.emailDelReportante = emailDelReportante;
		this.id = id;
	}
}