package ar.edu.unq.inscripcionunq.spring.dao;

import org.springframework.stereotype.Repository;

import ar.edu.unq.inscripcionunq.spring.model.Subject;

@Repository
public class SubjectDaoImp extends GenericDaoImp<Subject> implements GenericDao<Subject> {

	@Override
	protected Class<Subject> getDomainClass() {
		// TODO Auto-generated method stub
		return Subject.class;
	}

}
