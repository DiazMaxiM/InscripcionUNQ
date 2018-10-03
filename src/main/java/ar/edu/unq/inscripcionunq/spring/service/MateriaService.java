package ar.edu.unq.inscripcionunq.spring.service;

import java.util.List;

import ar.edu.unq.inscripcionunq.spring.exception.CodigoInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.DescripcionInvalidaException;
import ar.edu.unq.inscripcionunq.spring.exception.EstadoInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.ExisteMateriaConElMismoCodigoException;
import ar.edu.unq.inscripcionunq.spring.exception.IdNumberFormatException;
import ar.edu.unq.inscripcionunq.spring.exception.MateriaNoExisteException;
import ar.edu.unq.inscripcionunq.spring.model.Carrera;
import ar.edu.unq.inscripcionunq.spring.model.Materia;
import ar.edu.unq.inscripcionunq.spring.controller.miniobject.MateriaSistemaJson;

public interface MateriaService extends GenericService<Materia> {

	List<Materia> getMateriasParaCarreras(List<Carrera> carreras);

	void eliminarMateria(String idMateria) throws IdNumberFormatException, MateriaNoExisteException;
    
	void actualizarMateria(MateriaSistemaJson materiaJson) throws IdNumberFormatException, MateriaNoExisteException, ExisteMateriaConElMismoCodigoException;
    
    List<MateriaSistemaJson> getMateriasJson();
    
	void agregarNuevaMateria(MateriaSistemaJson materiaJson) throws DescripcionInvalidaException, CodigoInvalidoException, EstadoInvalidoException, ExisteMateriaConElMismoCodigoException, IdNumberFormatException, MateriaNoExisteException;
    
	void validarSiExisteMateriaConElMismoCodigo(String codigo) throws ExisteMateriaConElMismoCodigoException;

}