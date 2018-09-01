package ar.edu.unq.inscripcionunq.spring.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import ar.edu.unq.inscripcionunq.spring.model.Career;
import ar.edu.unq.inscripcionunq.spring.model.Subject;

@Repository
public class SubjectDaoImp extends GenericDaoImp<Subject> implements SubjectDao {

	@Override
	protected Class<Subject> getDomainClass() {
		// TODO Auto-generated method stub
		return Subject.class;
	}

	@Override
	public List<Subject> getSubjectsForCareers(List<Career> careers) {
		List<Long> careersId = new ArrayList<Long>();
		for (Career career : careers) {
			careersId.add(career.getId());
		}
		Session session = this.sessionFactory.getCurrentSession();
		Query<Subject> query = session.createQuery(
				"select distinct subject from Subject as subject join subject.careers c where c.id in (:careersId)");
		query.setParameterList("careersId", careersId);
		return query.getResultList();
	}

}
