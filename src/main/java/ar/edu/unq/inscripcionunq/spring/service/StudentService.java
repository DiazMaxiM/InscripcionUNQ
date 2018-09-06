package ar.edu.unq.inscripcionunq.spring.service;

import java.util.List;

import ar.edu.unq.inscripcionunq.spring.controller.miniobject.SubjectJson;
import ar.edu.unq.inscripcionunq.spring.exception.IdNumberFormatException;
import ar.edu.unq.inscripcionunq.spring.exception.StudentNotExistenException;
import ar.edu.unq.inscripcionunq.spring.model.Student;

public interface StudentService extends GenericService<Student> {

	List<SubjectJson> userApprovedSubjects(String idUser) throws IdNumberFormatException, StudentNotExistenException;

	void updateUserApprovedSubjects(String idUser, List<SubjectJson> studentsJson)
			throws IdNumberFormatException, StudentNotExistenException;

	List<SubjectJson> userDisapprovedSubjectsWithCommissionAvailable(String idUser)
			throws IdNumberFormatException, StudentNotExistenException;

}
