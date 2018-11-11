package ar.edu.unq.inscripcionunq.spring.service;

import java.util.List;

import ar.edu.unq.inscripcionunq.spring.controller.miniobject.IncidenciaJson;
import ar.edu.unq.inscripcionunq.spring.model.Incidencia;

public interface IncidenciaService extends GenericService<Incidencia> {

	public List<Incidencia> getIncidencias();

	public void agregarIncidencia(IncidenciaJson incidencia);

	public void actualizarIncidencia(IncidenciaJson incidencia);

	public List<IncidenciaJson> getIncidenciasJson();
}