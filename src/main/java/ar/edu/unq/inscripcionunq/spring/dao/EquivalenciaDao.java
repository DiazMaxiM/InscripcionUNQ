package ar.edu.unq.inscripcionunq.spring.dao;

import ar.edu.unq.inscripcionunq.spring.model.Equivalencia;
import ar.edu.unq.inscripcionunq.spring.model.Materia;

public interface EquivalenciaDao extends GenericDao<Equivalencia> {

	Equivalencia obtenerEquivalencia(Materia materiaOrigen, Materia materiaDestino);

}