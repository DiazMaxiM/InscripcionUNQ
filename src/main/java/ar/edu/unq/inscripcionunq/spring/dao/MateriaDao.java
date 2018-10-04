package ar.edu.unq.inscripcionunq.spring.dao;

import java.util.List;

import ar.edu.unq.inscripcionunq.spring.model.Carrera;
import ar.edu.unq.inscripcionunq.spring.model.Materia;

public interface MateriaDao extends GenericDao<Materia> {

	List<Materia> getMateriasParaCarreras(List<Carrera> carreras);

	Materia encontrarMateriaConElMismoCodigo(String codigo);
	
	List<Materia> getMateriasParaCarrera(Long idCarrera);

}
