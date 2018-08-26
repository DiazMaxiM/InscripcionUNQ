package ar.edu.unq.inscripcionunq.spring.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.inscripcionunq.spring.model.Subject;

@Service
@Transactional
public class SubjectServiceImp extends GenericServiceImp<Subject> implements GenericService<Subject> {

}
