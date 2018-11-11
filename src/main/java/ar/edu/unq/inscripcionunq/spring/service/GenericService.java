package ar.edu.unq.inscripcionunq.spring.service;

import java.util.List;

import ar.edu.unq.inscripcionunq.spring.exception.ObjectoNoEncontradoEnBDException;

public interface GenericService<T> {

	Long save(T c);

	T get(Long id) throws ObjectoNoEncontradoEnBDException;

	List<T> list();

	void update(T c);

	void delete(T c);
}