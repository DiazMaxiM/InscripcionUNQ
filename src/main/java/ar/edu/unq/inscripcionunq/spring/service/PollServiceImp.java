package ar.edu.unq.inscripcionunq.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.inscripcionunq.spring.dao.PollDao;
import ar.edu.unq.inscripcionunq.spring.model.Poll;

@Service
@Transactional(readOnly = true)
public class PollServiceImp implements PollService {

	@Autowired
	private PollDao pollDao;

	@Transactional
	@Override
	public long save(Poll poll) {
		return pollDao.save(poll);
	}

	@Override
	public Poll get(long id) {
		return pollDao.get(id);
	}

	@Override
	public List<Poll> list() {
		return pollDao.list();
	}

	@Transactional
	@Override
	public void update(long id, Poll poll) {
		pollDao.update(id, poll);
	}

	@Transactional
	@Override
	public void delete(long id) {
		pollDao.delete(id);
	}

}
