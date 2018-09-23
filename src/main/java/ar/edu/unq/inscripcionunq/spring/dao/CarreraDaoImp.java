package ar.edu.unq.inscripcionunq.spring.dao;

import org.springframework.stereotype.Repository;

import ar.edu.unq.inscripcionunq.spring.model.Carrera;


@Repository
public class CarreraDaoImp extends GenericDaoImp<Carrera> implements GenericDao<Carrera> {

	@Override
	protected Class<Carrera> getDomainClass() {
		return Carrera.class;
	}

}
