package ar.edu.unq.inscripcionunq.spring.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ar.edu.unq.inscripcionunq.spring.model.Student;

@Repository
public class StudentDaoImp implements StudentDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public long save(Student student) {
		sessionFactory.getCurrentSession().save(student);
		return student.getId();
	}

	@Override
	public Student get(long id) {
		return sessionFactory.getCurrentSession().get(Student.class, id);
	}

	@Override
	public List<Student> list() {
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Student> cq = cb.createQuery(Student.class);
		Root<Student> root = cq.from(Student.class);
		cq.select(root);
		Query<Student> query = session.createQuery(cq);
		return query.getResultList();
	}

	@Override
	public void update(long id, Student poll) {

	}

	@Override
	public void delete(long id) {

	}

}
