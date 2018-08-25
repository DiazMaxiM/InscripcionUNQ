package ar.edu.unq.inscripcionunq.spring.service;

import java.util.List;

import ar.edu.unq.inscripcionunq.spring.model.Poll;

public interface PollService {

	long save(Poll poll);

	Poll get(long id);

	List<Poll> list();

	void update(long id, Poll poll);

	void delete(long id);
}
