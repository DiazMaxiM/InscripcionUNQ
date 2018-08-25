package ar.edu.unq.inscripcionunq.spring.dao;

import java.util.List;

import ar.edu.unq.inscripcionunq.spring.model.Student;

public interface StudentDao {

	long save(Student student);

	Student get(long id);

	List<Student> list();

	void update(long id, Student student);

	void delete(long id);

}
