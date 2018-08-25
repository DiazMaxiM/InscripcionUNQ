package ar.edu.unq.inscripcionunq.spring.service;

import java.util.List;

import ar.edu.unq.inscripcionunq.spring.model.Matter;

public interface MatterService {

	long save(Matter matter);

	Matter get(long id);

	List<Matter> list();

	void update(long id, Matter matter);

	void delete(long id);
}
