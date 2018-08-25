package ar.edu.unq.inscripcionunq.spring.dao;

import java.util.List;

import ar.edu.unq.inscripcionunq.spring.model.Interval;

public interface IntervalDao {

	long save(Interval interval);

	Interval get(long id);

	List<Interval> list();

	void update(long id, Interval interval);

	void delete(long id);

}
