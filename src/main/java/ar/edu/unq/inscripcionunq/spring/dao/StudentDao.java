package ar.edu.unq.inscripcionunq.spring.dao;

import java.util.List;

import ar.edu.unq.inscripcionunq.spring.controller.miniobject.SubjectJson;
import ar.edu.unq.inscripcionunq.spring.model.Student;

public interface StudentDao extends GenericDao<Student> {

	List<SubjectJson> userDisapprovedSubjectsWithCommissionAvailable(Long idUser);
}
