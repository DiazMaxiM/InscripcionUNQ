package ar.edu.unq.inscripcionunq.spring.controller.miniobject;

import ar.edu.unq.inscripcionunq.spring.model.Incidencia;

public class IncidenciaJson {
	
	public Long id;
	public String descripcion;
	public TipoIncidenciaJson tipoIncidencia;
	public String tipoEstadoIncidencia;

	public IncidenciaJson() {
	
	}

	public IncidenciaJson(Incidencia incidencia) {
		this.id = incidencia.getId();
		this.tipoIncidencia = new TipoIncidenciaJson(incidencia.getTipoIncidencia());
		this.descripcion = incidencia.getDescripcion();
		this.tipoEstadoIncidencia = incidencia.getTipoEstadoIncidencia().getEstado();
	}
}