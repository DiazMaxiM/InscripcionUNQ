package ar.edu.unq.inscripcionunq.spring.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.inscripcionunq.spring.model.AcademicOffer;

@Repository
@Transactional
public class AcademicOfferDaoImp extends GenericDaoImp<AcademicOffer> implements GenericDao<AcademicOffer> {

	@Override
	protected Class<AcademicOffer> getDomainClass() {
		// TODO Auto-generated method stub
		return AcademicOffer.class;
	}

}
