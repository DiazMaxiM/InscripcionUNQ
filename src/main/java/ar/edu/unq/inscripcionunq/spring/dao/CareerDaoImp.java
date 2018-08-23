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

import ar.edu.unq.inscripcionunq.spring.model.Career;

@Repository
public class CareerDaoImp implements CareerDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public long save(Career career) {
		sessionFactory.getCurrentSession().save(career);
		return career.getId();
	}

	@Override
	public Career get(long id) {
		return sessionFactory.getCurrentSession().get(Career.class, id);
	}

	@Override
	public List<Career> list() {
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Career> cq = cb.createQuery(Career.class);
		Root<Career> root = cq.from(Career.class);
		cq.select(root);
		Query<Career> query = session.createQuery(cq);
		return query.getResultList();
	}

	@Override
	public void update(long id, Career career) {
		Session session = sessionFactory.getCurrentSession();
		Career career2 = session.byId(Career.class).load(id);
		career2.setCode(career.getCode());
		career2.setDescription(career.getDescription());
		session.flush();
	}

	@Override
	public void delete(long id) {
		Session session = sessionFactory.getCurrentSession();
		Career career = session.byId(Career.class).load(id);
		session.delete(career);
	}

}
