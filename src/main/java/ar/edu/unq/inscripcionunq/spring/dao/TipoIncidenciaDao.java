package ar.edu.unq.inscripcionunq.spring.dao;

import java.util.List;

import ar.edu.unq.inscripcionunq.spring.model.TipoIncidencia;

public interface TipoIncidenciaDao extends GenericDao<TipoIncidencia> {

	List<TipoIncidencia> getTipoIncidencias();
}