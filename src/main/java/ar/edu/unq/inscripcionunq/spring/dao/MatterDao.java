package ar.edu.unq.inscripcionunq.spring.dao;

import java.util.List;

import ar.edu.unq.inscripcionunq.spring.model.Matter;

public interface MatterDao {

	long save(Matter matter);

	Matter get(long id);

	List<Matter> list();

	void update(long id, Matter matter);

	void delete(long id);

}
