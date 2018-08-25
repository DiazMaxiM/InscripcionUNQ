package ar.edu.unq.inscripcionunq.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.inscripcionunq.spring.dao.StudentDao;
import ar.edu.unq.inscripcionunq.spring.model.Student;

@Service
@Transactional(readOnly = true)
public class StudentServiceImp implements StudentService {

	@Autowired
	private StudentDao studentDao;

	@Transactional
	@Override
	public long save(Student student) {
		return studentDao.save(student);
	}

	@Override
	public Student get(long id) {
		return studentDao.get(id);
	}

	@Override
	public List<Student> list() {
		return studentDao.list();
	}

	@Transactional
	@Override
	public void update(long id, Student student) {
		studentDao.update(id, student);
	}

	@Transactional
	@Override
	public void delete(long id) {
		studentDao.delete(id);
	}

}
