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

import ar.edu.unq.inscripcionunq.spring.model.AcademicOffer;

@Repository
public class AcademicOfferDaoImp implements AcademicOfferDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public long save(AcademicOffer academicOffer) {
		sessionFactory.getCurrentSession().save(academicOffer);
		return academicOffer.getId();
	}

	@Override
	public AcademicOffer get(long id) {
		return sessionFactory.getCurrentSession().get(AcademicOffer.class, id);
	}

	@Override
	public List<AcademicOffer> list() {
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<AcademicOffer> cq = cb.createQuery(AcademicOffer.class);
		Root<AcademicOffer> root = cq.from(AcademicOffer.class);
		cq.select(root);
		Query<AcademicOffer> query = session.createQuery(cq);
		return query.getResultList();
	}

	@Override
	public void update(long id, AcademicOffer academicOffer) {
		Session session = sessionFactory.getCurrentSession();
		AcademicOffer academicOffer2 = session.byId(AcademicOffer.class).load(id);

		session.flush();
	}

	@Override
	public void delete(long id) {

	}

}
