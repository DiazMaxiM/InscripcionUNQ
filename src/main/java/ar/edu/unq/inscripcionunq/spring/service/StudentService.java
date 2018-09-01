package ar.edu.unq.inscripcionunq.spring.service;

import java.util.List;

import ar.edu.unq.inscripcionunq.spring.controller.miniobject.SubjectJson;
import ar.edu.unq.inscripcionunq.spring.model.Student;

public interface StudentService extends GenericService<Student> {

	List<SubjectJson> userApprovedSubjects(String idUser);

	void updateUserApprovedSubjects(String idUser, List<SubjectJson> studentsJson);

}
