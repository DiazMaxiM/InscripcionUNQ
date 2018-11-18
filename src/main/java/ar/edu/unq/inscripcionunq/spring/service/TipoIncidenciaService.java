package ar.edu.unq.inscripcionunq.spring.service;

import java.util.List;

import ar.edu.unq.inscripcionunq.spring.controller.miniobject.TipoIncidenciaJson;
import ar.edu.unq.inscripcionunq.spring.exception.YaExisteTipoDeIncidenciaException;
import ar.edu.unq.inscripcionunq.spring.model.TipoIncidencia;

public interface TipoIncidenciaService extends GenericService<TipoIncidencia> {

	public List<TipoIncidencia> getTipoIncidencias();

	public void agregarNuevoTipoIncidencia(TipoIncidenciaJson tipoIncidenciaJson) throws YaExisteTipoDeIncidenciaException;

	public void actualizarTipoIncidencia(TipoIncidenciaJson tipoIncidenciaJson) throws YaExisteTipoDeIncidenciaException;

}