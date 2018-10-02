package ar.edu.unq.inscripcionunq.spring.service;

import java.util.List;

import ar.edu.unq.inscripcionunq.spring.exception.IdNumberFormatException;
import ar.edu.unq.inscripcionunq.spring.exception.MateriaNoExisteException;
import ar.edu.unq.inscripcionunq.spring.model.Carrera;
import ar.edu.unq.inscripcionunq.spring.model.Materia;
import ar.edu.unq.inscripcionunq.spring.controller.miniobject.MateriaSistemaJson;

public interface MateriaService extends GenericService<Materia> {

	List<Materia> getMateriasParaCarreras(List<Carrera> carreras);

	
	void eliminarMateria(String idMateria) throws IdNumberFormatException, MateriaNoExisteException;
    void actualizarMateria(MateriaSistemaJson materiaJson) throws IdNumberFormatException, MateriaNoExisteException;
    List<MateriaSistemaJson> getMateriasJson();

}
