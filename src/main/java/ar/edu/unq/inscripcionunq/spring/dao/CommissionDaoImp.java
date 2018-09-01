package ar.edu.unq.inscripcionunq.spring.dao;

import org.springframework.stereotype.Repository;

import ar.edu.unq.inscripcionunq.spring.model.Commission;

@Repository

public class CommissionDaoImp extends GenericDaoImp<Commission> implements GenericDao<Commission> {

	@Override
	protected Class<Commission> getDomainClass() {
		// TODO Auto-generated method stub
		return Commission.class;
	}

}
