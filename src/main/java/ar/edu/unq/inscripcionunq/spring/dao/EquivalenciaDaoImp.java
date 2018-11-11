package ar.edu.unq.inscripcionunq.spring.dao;

import org.springframework.stereotype.Repository;

import ar.edu.unq.inscripcionunq.spring.model.Equivalencia;

@Repository
public class EquivalenciaDaoImp extends GenericDaoImp<Equivalencia> implements EquivalenciaDao {

	@Override
	protected Class<Equivalencia> getDomainClass() {
		return Equivalencia.class;
	}
}