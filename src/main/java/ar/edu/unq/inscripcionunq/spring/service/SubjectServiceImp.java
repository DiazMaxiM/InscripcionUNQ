package ar.edu.unq.inscripcionunq.spring.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.inscripcionunq.spring.dao.SubjectDao;
import ar.edu.unq.inscripcionunq.spring.model.Career;
import ar.edu.unq.inscripcionunq.spring.model.Subject;

@Service
@Transactional

public class SubjectServiceImp extends GenericServiceImp<Subject> implements SubjectService {

	@Override
	@Transactional

	public List<Subject> getSubjectsForCareers(List<Career> careers) {
		return ((SubjectDao) genericDao).getSubjectsForCareers(careers);
	}

}
