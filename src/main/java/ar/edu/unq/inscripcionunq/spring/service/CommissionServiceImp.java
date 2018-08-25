package ar.edu.unq.inscripcionunq.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.inscripcionunq.spring.dao.CommissionDao;
import ar.edu.unq.inscripcionunq.spring.model.Commission;

@Service
@Transactional(readOnly = true)
public class CommissionServiceImp implements CommissionService {

	@Autowired
	private CommissionDao commissionDao;

	@Transactional
	@Override
	public long save(Commission commission) {
		return commissionDao.save(commission);
	}

	@Override
	public Commission get(long id) {
		return commissionDao.get(id);
	}

	@Override
	public List<Commission> list() {
		return commissionDao.list();
	}

	@Transactional
	@Override
	public void update(long id, Commission commission) {
		commissionDao.update(id, commission);
	}

	@Transactional
	@Override
	public void delete(long id) {
		commissionDao.delete(id);
	}

}
