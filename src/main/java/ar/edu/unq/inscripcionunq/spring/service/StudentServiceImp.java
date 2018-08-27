package ar.edu.unq.inscripcionunq.spring.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.inscripcionunq.spring.model.Student;

@Service
@Transactional
public class StudentServiceImp extends GenericServiceImp<Student> implements StudentService {

}
