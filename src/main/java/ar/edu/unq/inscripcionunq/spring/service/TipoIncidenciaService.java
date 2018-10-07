package ar.edu.unq.inscripcionunq.spring.service;

import java.util.List;

import ar.edu.unq.inscripcionunq.spring.controller.miniobject.TipoIncidenciaJson;
import ar.edu.unq.inscripcionunq.spring.model.TipoIncidencia;

public interface TipoIncidenciaService extends GenericService<TipoIncidencia> {

	public List<TipoIncidencia> getTipoIncidencias();

	public void agregarNuevoTipoIncidencia(TipoIncidenciaJson tipoIncidenciaJson);

	public void actualizarTipoIncidencia(TipoIncidenciaJson tipoIncidenciaJson);
}
