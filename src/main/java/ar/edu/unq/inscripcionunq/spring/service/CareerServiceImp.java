package ar.edu.unq.inscripcionunq.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.inscripcionunq.spring.dao.CareerDao;
import ar.edu.unq.inscripcionunq.spring.model.Career;

@Service
@Transactional(readOnly = true)
public class CareerServiceImp implements CareerService {

	@Autowired
	private CareerDao careerDao;

	@Transactional
	@Override
	public long save(Career career) {
		return careerDao.save(career);
	}

	@Override
	public Career get(long id) {
		return careerDao.get(id);
	}

	@Override
	public List<Career> list() {
		return careerDao.list();
	}

	@Transactional
	@Override
	public void update(long id, Career career) {
		careerDao.update(id, career);
	}

	@Transactional
	@Override
	public void delete(long id) {
		careerDao.delete(id);
	}

}
