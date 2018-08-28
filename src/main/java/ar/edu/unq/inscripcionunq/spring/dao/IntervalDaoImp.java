package ar.edu.unq.inscripcionunq.spring.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.inscripcionunq.spring.model.Interval;

@Repository
@Transactional

public class IntervalDaoImp extends GenericDaoImp<Interval> implements GenericDao<Interval> {

	@Override
	protected Class<Interval> getDomainClass() {
		// TODO Auto-generated method stub
		return Interval.class;
	}

}
