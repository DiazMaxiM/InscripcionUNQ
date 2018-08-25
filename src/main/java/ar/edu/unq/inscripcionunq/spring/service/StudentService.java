package ar.edu.unq.inscripcionunq.spring.service;

import java.util.List;

import ar.edu.unq.inscripcionunq.spring.model.Student;

public interface StudentService {

	long save(Student stundent);

	Student get(long id);

	List<Student> list();

	void update(long id, Student stundent);

	void delete(long id);
}
