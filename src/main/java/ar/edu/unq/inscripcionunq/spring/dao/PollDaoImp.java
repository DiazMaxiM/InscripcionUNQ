package ar.edu.unq.inscripcionunq.spring.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ar.edu.unq.inscripcionunq.spring.model.Poll;

@Repository
public class PollDaoImp implements PollDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public long save(Poll poll) {
		sessionFactory.getCurrentSession().save(poll);
		return poll.getId();
	}

	@Override
	public Poll get(long id) {
		return sessionFactory.getCurrentSession().get(Poll.class, id);
	}

	@Override
	public List<Poll> list() {
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Poll> cq = cb.createQuery(Poll.class);
		Root<Poll> root = cq.from(Poll.class);
		cq.select(root);
		Query<Poll> query = session.createQuery(cq);
		return query.getResultList();
	}

	@Override
	public void update(long id, Poll poll) {
		Session session = sessionFactory.getCurrentSession();
		Poll poll2 = session.byId(Poll.class).load(id);

		session.flush();
	}

	@Override
	public void delete(long id) {

	}

}
