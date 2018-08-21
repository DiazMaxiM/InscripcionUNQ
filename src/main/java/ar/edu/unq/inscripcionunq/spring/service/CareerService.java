package ar.edu.unq.inscripcionunq.spring.service;

import java.util.List;

import ar.edu.unq.inscripcionunq.spring.model.Career;

public interface CareerService {

	long save(Career career);

	Career get(long id);

	List<Career> list();

	void update(long id, Career career);

	void delete(long id);
}
