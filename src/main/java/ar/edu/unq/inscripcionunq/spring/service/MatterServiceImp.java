package ar.edu.unq.inscripcionunq.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.inscripcionunq.spring.dao.MatterDao;
import ar.edu.unq.inscripcionunq.spring.model.Matter;

@Service
@Transactional(readOnly = true)
public class MatterServiceImp implements MatterService {

	@Autowired
	private MatterDao matterDao;

	@Transactional
	@Override
	public long save(Matter matter) {
		return matterDao.save(matter);
	}

	@Override
	public Matter get(long id) {
		return matterDao.get(id);
	}

	@Override
	public List<Matter> list() {
		return matterDao.list();
	}

	@Transactional
	@Override
	public void update(long id, Matter matter) {
		matterDao.update(id, matter);
	}

	@Transactional
	@Override
	public void delete(long id) {
		matterDao.delete(id);
	}

}
