package ar.edu.unq.inscripcionunq.spring.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import ar.edu.unq.inscripcionunq.spring.exception.UserInPollNotFoundException;
import ar.edu.unq.inscripcionunq.spring.model.Poll;
import ar.edu.unq.inscripcionunq.spring.model.Student;

@Repository

public class PollDaoImp extends GenericDaoImp<Poll> implements PollDao {

	@Override
	protected Class<Poll> getDomainClass() {
		return Poll.class;
	}

	public List<Poll> getAllPollsActiveForDni(String dni) {
		Session session = this.sessionFactory.getCurrentSession();
		return session.createQuery("select p from Poll p join p.students s where s.dni=:dni ").setParameter("dni", dni)
				.getResultList();
	}

	@Override
	public Student getUserDataForPoll(String dni, Long idPoll) throws UserInPollNotFoundException {
		Session session = this.sessionFactory.getCurrentSession();
		Query<Student> query = session.createQuery(
				"select student from Poll as poll inner join poll.students student where poll.id=:idPoll and student.dni=:dni  ");
		query.setParameter("idPoll", idPoll);
		query.setParameter("dni", dni);
		List<Student> results = query.getResultList();
		if (results.isEmpty()) {
			throw new UserInPollNotFoundException();
		}
		return query.getResultList().get(0);
	}
}
