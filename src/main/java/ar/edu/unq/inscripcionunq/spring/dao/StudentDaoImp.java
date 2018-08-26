package ar.edu.unq.inscripcionunq.spring.dao;

import org.springframework.stereotype.Repository;

import ar.edu.unq.inscripcionunq.spring.model.Student;

@Repository
public class StudentDaoImp extends GenericDaoImp<Student> implements GenericDao<Student> {

	@Override
	protected Class<Student> getDomainClass() {
		// TODO Auto-generated method stub
		return Student.class;
	}

}
