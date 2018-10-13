package ar.edu.unq.inscripcionunq.spring.service;

import java.util.List;

import ar.edu.unq.inscripcionunq.spring.controller.miniobject.MateriaSistemaJson;
import ar.edu.unq.inscripcionunq.spring.exception.CodigoInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.DescripcionInvalidaException;
import ar.edu.unq.inscripcionunq.spring.exception.EstadoInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.ExisteMateriaConElMismoCodigoException;
import ar.edu.unq.inscripcionunq.spring.exception.HorarioInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.IdNumberFormatException;
import ar.edu.unq.inscripcionunq.spring.exception.MateriaNoExisteException;
import ar.edu.unq.inscripcionunq.spring.exception.NombreInvalidoException;
import ar.edu.unq.inscripcionunq.spring.model.Carrera;
import ar.edu.unq.inscripcionunq.spring.model.Materia;

public interface MateriaService extends GenericService<Materia> {

	List<Materia> getMateriasParaCarreras(List<Carrera> carreras);

	void actualizarMateria(MateriaSistemaJson materiaJson) throws IdNumberFormatException, MateriaNoExisteException,
			ExisteMateriaConElMismoCodigoException, CodigoInvalidoException, NombreInvalidoException,
			EstadoInvalidoException, DescripcionInvalidaException, HorarioInvalidoException;

	List<MateriaSistemaJson> getMateriasJson();

	void agregarNuevaMateria(MateriaSistemaJson materiaJson)
			throws DescripcionInvalidaException, CodigoInvalidoException, EstadoInvalidoException,
			ExisteMateriaConElMismoCodigoException, IdNumberFormatException, MateriaNoExisteException;

	void validarSiExisteMateriaConElMismoCodigo(String codigo) throws ExisteMateriaConElMismoCodigoException;

	List<MateriaSistemaJson> getMateriasParaCarrera(String idCarrera);

	Materia getMateriaPorCodigo(String codigo);

}