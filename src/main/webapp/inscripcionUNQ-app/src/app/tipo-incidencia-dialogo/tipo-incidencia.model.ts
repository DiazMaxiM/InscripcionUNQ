export class TipoIncidencia {
	id?: string;
	descripcion?: string;
	estado?: string;

	constructor(
		descripcion?: string,
		estado?: string,
		id?: string
	) {
		this.descripcion = descripcion;
		this.estado = estado;
		this.id = id;
	}
}