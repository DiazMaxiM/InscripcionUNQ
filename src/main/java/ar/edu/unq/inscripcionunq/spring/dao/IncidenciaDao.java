package ar.edu.unq.inscripcionunq.spring.dao;

import java.util.List;

import ar.edu.unq.inscripcionunq.spring.model.Incidencia;

public interface IncidenciaDao extends GenericDao<Incidencia> {

	List<Incidencia> getIncidencias();
}