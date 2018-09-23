package ar.edu.unq.inscripcionunq.spring.dao;

import org.springframework.stereotype.Repository;

import ar.edu.unq.inscripcionunq.spring.model.Horario;

@Repository

public class HorarioDaoImp extends GenericDaoImp<Horario> implements GenericDao<Horario> {

	@Override
	protected Class<Horario> getDomainClass() {
		return Horario.class;
	}

}
