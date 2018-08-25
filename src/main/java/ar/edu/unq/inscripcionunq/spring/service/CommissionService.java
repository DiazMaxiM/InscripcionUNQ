package ar.edu.unq.inscripcionunq.spring.service;

import java.util.List;

import ar.edu.unq.inscripcionunq.spring.model.Commission;

public interface CommissionService {

	long save(Commission commission);

	Commission get(long id);

	List<Commission> list();

	void update(long id, Commission commission);

	void delete(long id);
}
