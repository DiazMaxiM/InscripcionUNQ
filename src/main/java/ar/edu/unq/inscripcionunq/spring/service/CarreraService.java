package ar.edu.unq.inscripcionunq.spring.service;

import ar.edu.unq.inscripcionunq.spring.exception.CarreraNoExisteException;
import ar.edu.unq.inscripcionunq.spring.exception.IdNumberFormatException;
import ar.edu.unq.inscripcionunq.spring.model.Carrera;

public interface CarreraService extends GenericService<Carrera> {

	void eliminarCarrera(String idCarrera) throws IdNumberFormatException, CarreraNoExisteException;

}
