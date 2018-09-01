package ar.edu.unq.inscripcionunq.spring.service;

import java.util.List;

import ar.edu.unq.inscripcionunq.spring.model.Career;
import ar.edu.unq.inscripcionunq.spring.model.Subject;

public interface SubjectService extends GenericService<Subject> {

	List<Subject> getSubjectsForCareers(List<Career> careers);

}
