package ar.edu.unq.inscripcionunq.spring.service;

import java.util.List;

import ar.edu.unq.inscripcionunq.spring.controller.miniobject.EquivalenciaJson;
import ar.edu.unq.inscripcionunq.spring.exception.ObjectoNoEncontradoEnBDException;
import ar.edu.unq.inscripcionunq.spring.model.Equivalencia;

public interface EquivalenciaService extends GenericService<Equivalencia> {

	List<Equivalencia> getEquivalencias();

	void agregarNuevaCarrera(EquivalenciaJson equivalenciaJson) throws ObjectoNoEncontradoEnBDException;

	void actualizarEquivalencia(EquivalenciaJson equivalenciaJson) throws ObjectoNoEncontradoEnBDException;

}
