package ar.edu.unq.inscripcionunq.spring.dao;

import java.util.List;

import ar.edu.unq.inscripcionunq.spring.model.Carrera;
import ar.edu.unq.inscripcionunq.spring.model.Comision;
import ar.edu.unq.inscripcionunq.spring.model.OfertaAcademica;

public interface OfertaAcademicaDao extends GenericDao<OfertaAcademica>{
	
	public Carrera getCarreraEnOferta(Long idOferta);

	public List<Comision> getComisionesEnOferta(Long id);

	public OfertaAcademica getOfertaPorNombre(String nombre);

	public List<OfertaAcademica> getOfertasParaPeriodo(Long idPeriodo);
} 