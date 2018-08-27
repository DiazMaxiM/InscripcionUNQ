package ar.edu.unq.inscripcionunq.spring.service;

import java.util.List;

public interface GenericService<T> {

	Long save(T c);

	T get(Long id);

	List<T> list();

	void update(T c);

	void delete(Long id);
}
