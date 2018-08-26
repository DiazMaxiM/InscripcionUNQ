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

import ar.edu.unq.inscripcionunq.spring.model.BaseEntity;

@Repository
public abstract class GenericDaoImp<T> implements GenericDao<T> {
	@Autowired
	protected SessionFactory sessionFactory;
	protected Class<T> persistentClass = this.getDomainClass();

	public Long save(T object) {
		sessionFactory.getCurrentSession().save(object);
		return (Long) ((BaseEntity) object).getId();
	}

	protected abstract Class<T> getDomainClass();

	public T get(Long id) {
		return sessionFactory.getCurrentSession().get(persistentClass, id);
	}

	public List<T> list() {
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(persistentClass);
		Root<T> root = cq.from(persistentClass);
		cq.select(root);
		Query<T> query = session.createQuery(cq);
		return query.getResultList();
	}

}
