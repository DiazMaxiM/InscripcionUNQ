package ar.edu.unq.inscripcionunq.spring.dao;

import org.springframework.stereotype.Repository;

import ar.edu.unq.inscripcionunq.spring.model.Poll;

@Repository
public class PollDaoImp extends GenericDaoImp<Poll> implements GenericDao<Poll> {

	@Override
	protected Class<Poll> getDomainClass() {
		// TODO Auto-generated method stub
		return Poll.class;
	}

}
