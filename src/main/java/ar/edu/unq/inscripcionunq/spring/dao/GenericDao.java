package ar.edu.unq.inscripcionunq.spring.dao;

import java.util.List;

public interface GenericDao<T> {

	Long save(T c);

	T get(Long id);

	List<T> list();

}
