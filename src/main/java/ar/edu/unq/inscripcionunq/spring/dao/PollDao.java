package ar.edu.unq.inscripcionunq.spring.dao;

import java.util.List;

import ar.edu.unq.inscripcionunq.spring.model.Poll;

public interface PollDao extends GenericDao<Poll> {
	List<Poll> getAllPollsActiveForDni(String dni);

}
