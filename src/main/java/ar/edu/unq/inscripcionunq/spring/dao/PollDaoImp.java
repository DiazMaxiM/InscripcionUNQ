package ar.edu.unq.inscripcionunq.spring.dao;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.inscripcionunq.spring.model.Poll;

@Repository
@Transactional
public class PollDaoImp extends GenericDaoImp<Poll> implements PollDao {

	@Override
	protected Class<Poll> getDomainClass() {
		// TODO Auto-generated method stub
		return Poll.class;
	}

	public List<Poll> getAllPollsActiveForDni(String dni) {
		Session session = this.sessionFactory.openSession();
		return session.createQuery("from Poll").getResultList();
	}
}
