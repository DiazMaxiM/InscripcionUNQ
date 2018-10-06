package ar.edu.unq.inscripcionunq.spring.dao;

import org.springframework.stereotype.Repository;

import ar.edu.unq.inscripcionunq.spring.model.Periodo;


@Repository
public class PeriodoDao extends GenericDaoImp<Periodo> implements GenericDao<Periodo>{

	@Override
	protected Class<Periodo> getDomainClass() {
		return Periodo.class;
	}

}
