package ar.edu.unq.inscripcionunq.spring.dao;

import java.util.List;

import ar.edu.unq.inscripcionunq.spring.model.Career;
import ar.edu.unq.inscripcionunq.spring.model.Subject;

public interface SubjectDao extends GenericDao<Subject> {
	List<Subject> getSubjectsForCareers(List<Career> careers);
}
