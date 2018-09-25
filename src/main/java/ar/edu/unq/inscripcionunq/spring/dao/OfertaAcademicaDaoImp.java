package ar.edu.unq.inscripcionunq.spring.dao;

import org.springframework.stereotype.Repository;

import ar.edu.unq.inscripcionunq.spring.model.OfertaAcademica;

@Repository
public class OfertaAcademicaDaoImp extends GenericDaoImp<OfertaAcademica> implements GenericDao<OfertaAcademica> {

	@Override
	protected Class<OfertaAcademica> getDomainClass() {
		return OfertaAcademica.class;
	}

}
