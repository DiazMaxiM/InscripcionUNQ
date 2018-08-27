package ar.edu.unq.inscripcionunq.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.inscripcionunq.spring.dao.GenericDao;

public abstract class GenericServiceImp<T> implements GenericService<T> {

	@Autowired
	protected GenericDao<T> genericDao;

	@Transactional
	public Long save(T c) {
		return genericDao.save(c);
	}

	@Transactional
	public T get(Long id) {
		return (T) genericDao.get(id);
	}

	@Transactional
	public List<T> list() {
		return genericDao.list();
	}

	@Transactional
	public void update(Long id, T c) {
	}

	@Transactional
	public void delete(Long id) {
	}

}
