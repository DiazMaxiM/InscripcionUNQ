package ar.edu.unq.inscripcionunq.spring.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import ar.edu.unq.inscripcionunq.spring.controller.miniobject.SubjectJson;
import ar.edu.unq.inscripcionunq.spring.model.Student;

@Repository

public class StudentDaoImp extends GenericDaoImp<Student> implements StudentDao {

	@Override
	protected Class<Student> getDomainClass() {
		// TODO Auto-generated method stub
		return Student.class;
	}

	@Override
	public List<SubjectJson> userDisapprovedSubjectsWithCommissionAvailable(Long idUser) {
		return null;
	}

}
