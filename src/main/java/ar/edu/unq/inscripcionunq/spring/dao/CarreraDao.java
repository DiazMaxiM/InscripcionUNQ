package ar.edu.unq.inscripcionunq.spring.dao;

import ar.edu.unq.inscripcionunq.spring.model.Carrera;

public interface CarreraDao extends GenericDao<Carrera> {

	Carrera encontrarCarreraConElMismoCodigo(String codigo);
}