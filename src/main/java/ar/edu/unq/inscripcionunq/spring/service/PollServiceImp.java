package ar.edu.unq.inscripcionunq.spring.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.inscripcionunq.spring.dao.PollDao;
import ar.edu.unq.inscripcionunq.spring.model.Poll;

@Service
@Transactional
public class PollServiceImp extends GenericServiceImp<Poll> implements PollService {

	@Override
	public List<Poll> getAllPollsActiveForDni(String dni) {
		return ((PollDao) genericDao).getAllPollsActiveForDni(dni);
	}

}
