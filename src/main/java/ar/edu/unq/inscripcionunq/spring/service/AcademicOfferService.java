package ar.edu.unq.inscripcionunq.spring.service;

import java.util.List;

import ar.edu.unq.inscripcionunq.spring.model.AcademicOffer;

public interface AcademicOfferService {

	long save(AcademicOffer academicOffer);

	AcademicOffer get(long id);

	List<AcademicOffer> list();

	void update(long id, AcademicOffer academicOffer);

	void delete(long id);
}
