package ar.edu.unq.inscripcionunq.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.inscripcionunq.spring.dao.AcademicOfferDao;
import ar.edu.unq.inscripcionunq.spring.model.AcademicOffer;

@Service
@Transactional(readOnly = true)
public class AcademicOfferServiceImp implements AcademicOfferService {

	@Autowired
	private AcademicOfferDao academicOfferDao;

	@Transactional
	@Override
	public long save(AcademicOffer academicOffer) {
		return academicOfferDao.save(academicOffer);
	}

	@Override
	public AcademicOffer get(long id) {
		return academicOfferDao.get(id);
	}

	@Override
	public List<AcademicOffer> list() {
		return academicOfferDao.list();
	}

	@Transactional
	@Override
	public void update(long id, AcademicOffer commission) {
		academicOfferDao.update(id, commission);
	}

	@Transactional
	@Override
	public void delete(long id) {
		academicOfferDao.delete(id);
	}

}
