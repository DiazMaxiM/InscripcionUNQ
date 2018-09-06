package ar.edu.unq.inscripcionunq.spring.dao;

import java.util.List;

import ar.edu.unq.inscripcionunq.spring.exception.UserInPollNotFoundException;
import ar.edu.unq.inscripcionunq.spring.model.Poll;
import ar.edu.unq.inscripcionunq.spring.model.Student;

public interface PollDao extends GenericDao<Poll> {
	List<Poll> getAllPollsActiveForDni(String dni);

	Student getUserDataForPoll(String dni, Long idPoll) throws UserInPollNotFoundException;

}
