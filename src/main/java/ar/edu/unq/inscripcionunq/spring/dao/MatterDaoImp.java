package ar.edu.unq.inscripcionunq.spring.dao;

import org.springframework.stereotype.Repository;

import ar.edu.unq.inscripcionunq.spring.model.Matter;

@Repository
public class MatterDaoImp extends GenericDaoImp<Matter> implements GenericDao<Matter> {

	@Override
	protected Class<Matter> getDomainClass() {
		// TODO Auto-generated method stub
		return Matter.class;
	}

}
