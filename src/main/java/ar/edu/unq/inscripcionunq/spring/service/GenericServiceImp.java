package ar.edu.unq.inscripcionunq.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.inscripcionunq.spring.dao.GenericDao;
import ar.edu.unq.inscripcionunq.spring.exception.ObjectNotFoundinDBException;

public abstract class GenericServiceImp<T> implements GenericService<T> {

	@Autowired
	protected GenericDao<T> genericDao;

	@Transactional
	public Long save(T c) {
		return genericDao.save(c);
	}

	@Transactional
	public T get(Long id) throws ObjectNotFoundinDBException {
		T object = genericDao.get(id);
		if (object == null) {
			throw new ObjectNotFoundinDBException();
		}
		return object;
	}

	@Transactional
	public List<T> list() {
		return genericDao.list();
	}

	@Transactional
	public void update(T c) {
		genericDao.update(c);

	}

	@Transactional
	public void delete(T c) {
		genericDao.delete(c);
	}

}
