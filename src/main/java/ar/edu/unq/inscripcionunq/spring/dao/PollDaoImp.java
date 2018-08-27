package ar.edu.unq.inscripcionunq.spring.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.inscripcionunq.spring.model.Poll;
import ar.edu.unq.inscripcionunq.spring.model.Student;

@Repository
@Transactional
public class PollDaoImp extends GenericDaoImp<Poll> implements PollDao {

	@Override
	protected Class<Poll> getDomainClass() {
		// TODO Auto-generated method stub
		return Poll.class;
	}

	@Transactional
	public List<Poll> getAllPollsActiveForDni(String dni) {
		Session session = this.sessionFactory.openSession();
		return session.createQuery("from Poll").getResultList();
	}

	@Transactional
	@Override
	public Student getUserDataForPoll(String dni, Long idPoll) {
		Session session = this.sessionFactory.openSession();
		Query query = session.createQuery(
				"select student from Poll as poll inner join poll.students student where poll.id=:idPoll and student.dni=:dni  ");
		query.setParameter("idPoll", idPoll);
		query.setParameter("dni", dni);
		return (Student) query.getSingleResult();
	}
}
