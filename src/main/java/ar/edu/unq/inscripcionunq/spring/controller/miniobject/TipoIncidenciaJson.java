package ar.edu.unq.inscripcionunq.spring.controller.miniobject;

import ar.edu.unq.inscripcionunq.spring.model.TipoIncidencia;

public class TipoIncidenciaJson {
	public Long id;
	public String descripcion;
	public String estado;

	public TipoIncidenciaJson() {
	}

	public TipoIncidenciaJson(TipoIncidencia tipoIncidencia) {
		this.id = tipoIncidencia.getId();
		this.descripcion = tipoIncidencia.getDescripcion();
		this.estado = tipoIncidencia.getEstado().toString();
	}

}
