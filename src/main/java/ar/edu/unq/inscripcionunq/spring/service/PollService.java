package ar.edu.unq.inscripcionunq.spring.service;

import java.util.List;

import ar.edu.unq.inscripcionunq.spring.model.Poll;

public interface PollService extends GenericService<Poll> {

	public List<Poll> getAllPollsActiveForDni(String dni);
}
