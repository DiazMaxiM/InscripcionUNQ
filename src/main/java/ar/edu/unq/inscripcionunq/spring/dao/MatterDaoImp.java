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

import ar.edu.unq.inscripcionunq.spring.model.Matter;

@Repository
public class MatterDaoImp implements MatterDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public long save(Matter matter) {
		sessionFactory.getCurrentSession().save(matter);
		return matter.getId();
	}

	@Override
	public Matter get(long id) {
		return sessionFactory.getCurrentSession().get(Matter.class, id);
	}

	@Override
	public List<Matter> list() {
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Matter> cq = cb.createQuery(Matter.class);
		Root<Matter> root = cq.from(Matter.class);
		cq.select(root);
		Query<Matter> query = session.createQuery(cq);
		return query.getResultList();
	}

	@Override
	public void update(long id, Matter matter) {
		Session session = sessionFactory.getCurrentSession();
		Matter matter2 = session.byId(Matter.class).load(id);
		matter2.setCode(matter.getCode());
		matter2.setHours(matter.getHours());
		matter2.setName(matter.getName());
		session.flush();
	}

	@Override
	public void delete(long id) {
		Session session = sessionFactory.getCurrentSession();
		Matter matter = session.byId(Matter.class).load(id);
		matter.disabled();
		update(id, matter);
	}

}
