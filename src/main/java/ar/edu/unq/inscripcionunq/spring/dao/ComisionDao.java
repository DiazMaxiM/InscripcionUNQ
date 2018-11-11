package ar.edu.unq.inscripcionunq.spring.dao;

import java.util.List;

import ar.edu.unq.inscripcionunq.spring.model.Comision;

public interface ComisionDao extends GenericDao<Comision> {

	List<Comision> getComisionParaMateriaEnEncuesta(Long idMateria, Long idEncuesta);

	List<Comision> getTodasLasComisionesDeMateriaEnEncuesta(Long idEncuesta);

	List<Comision> getComisionParaPeriodo(Long idPeriodo);

	Comision obtenerComisionConNombreEnPeriodo(String nombre, Long id);
}