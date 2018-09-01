package ar.edu.unq.inscripcionunq.spring.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.inscripcionunq.spring.dao.PollDao;
import ar.edu.unq.inscripcionunq.spring.model.Poll;
import ar.edu.unq.inscripcionunq.spring.model.Student;

@Service
@Transactional

public class PollServiceImp extends GenericServiceImp<Poll> implements PollService {

	@Override
	@Transactional

	public List<Poll> getAllPollsActiveForDni(String dni) {
		return ((PollDao) genericDao).getAllPollsActiveForDni(dni);
	}

	@Override
	@Transactional
	public Student getUserDataForPoll(String dni, Long idPoll) {
		return ((PollDao) genericDao).getUserDataForPoll(dni, idPoll);
	}

}
