package ar.edu.unq.inscripcionunq.spring.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.inscripcionunq.spring.model.Student;

@Repository
@Transactional
public class StudentDaoImp extends GenericDaoImp<Student> implements GenericDao<Student> {

	@Override
	protected Class<Student> getDomainClass() {
		// TODO Auto-generated method stub
		return Student.class;
	}

}