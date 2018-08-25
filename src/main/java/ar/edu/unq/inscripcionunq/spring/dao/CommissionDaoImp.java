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

import ar.edu.unq.inscripcionunq.spring.model.Commission;

@Repository
public class CommissionDaoImp implements CommissionDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public long save(Commission commission) {
		sessionFactory.getCurrentSession().save(commission);
		return commission.getId();
	}

	@Override
	public Commission get(long id) {
		return sessionFactory.getCurrentSession().get(Commission.class, id);
	}

	@Override
	public List<Commission> list() {
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Commission> cq = cb.createQuery(Commission.class);
		Root<Commission> root = cq.from(Commission.class);
		cq.select(root);
		Query<Commission> query = session.createQuery(cq);
		return query.getResultList();
	}

	@Override
	public void update(long id, Commission commission) {
		Session session = sessionFactory.getCurrentSession();
		Commission commission2 = session.byId(Commission.class).load(id);

		session.flush();
	}

	@Override
	public void delete(long id) {
		Session session = sessionFactory.getCurrentSession();
		Commission commission = session.byId(Commission.class).load(id);
		commission.disabled();
		update(id, commission);
	}

}
