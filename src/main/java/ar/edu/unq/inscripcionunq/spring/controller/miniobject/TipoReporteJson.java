package ar.edu.unq.inscripcionunq.spring.controller.miniobject;

import ar.edu.unq.inscripcionunq.spring.model.TipoReporte;

public class TipoReporteJson {
	public String id;
	public String descripcion;

	public TipoReporteJson() {
	}

	public TipoReporteJson(TipoReporte tipoReporte) {
		this.id = TipoReporte.getEnum(tipoReporte.getDescripcion()).toString();
		this.descripcion = tipoReporte.getDescripcion();
	}
}
