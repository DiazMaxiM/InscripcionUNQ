package ar.edu.unq.inscripcionunq.spring.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import ar.edu.unq.inscripcionunq.spring.model.Commission;

@Repository

public class CommissionDaoImp extends GenericDaoImp<Commission> implements CommissionDao {

	@Override
	protected Class<Commission> getDomainClass() {
		// TODO Auto-generated method stub
		return Commission.class;
	}

	@Override
	public List<Commission> getCommissionForSubjectInPoll(Long idSubject, Long idPoll) {
		Session session = this.sessionFactory.getCurrentSession();
		Query<Commission> query = session.createQuery("select distinct c from Poll p join p.academicsOffer o "
				+ "join o.commissions c join c.subject s where p.id=:idPoll and s.id= :idSubject");
		query.setParameter("idPoll", idPoll);
		query.setParameter("idSubject", idSubject);
		return query.getResultList();
	}

}
