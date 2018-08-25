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

import ar.edu.unq.inscripcionunq.spring.model.Interval;

@Repository
public class IntervalDaoImp implements IntervalDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public long save(Interval interval) {
		sessionFactory.getCurrentSession().save(interval);
		return interval.getId();
	}

	@Override
	public Interval get(long id) {
		return sessionFactory.getCurrentSession().get(Interval.class, id);
	}

	@Override
	public List<Interval> list() {
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Interval> cq = cb.createQuery(Interval.class);
		Root<Interval> root = cq.from(Interval.class);
		cq.select(root);
		Query<Interval> query = session.createQuery(cq);
		return query.getResultList();
	}

	@Override
	public void update(long id, Interval interval) {
		Session session = sessionFactory.getCurrentSession();
		Interval interval2 = session.byId(Interval.class).load(id);
		session.flush();
	}

	@Override
	public void delete(long id) {
		// TODO Auto-generated method stub

	}

}
