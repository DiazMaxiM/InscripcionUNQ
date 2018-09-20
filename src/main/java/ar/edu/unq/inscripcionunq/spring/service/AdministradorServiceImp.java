package ar.edu.unq.inscripcionunq.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.inscripcionunq.spring.dao.AdministradorDao;
import ar.edu.unq.inscripcionunq.spring.exception.ObjectNotFoundinDBException;
import ar.edu.unq.inscripcionunq.spring.model.Administrador;

@Service
@Transactional
public class AdministradorServiceImp extends GenericServiceImp<Administrador> implements AdministradorService {
    
	@Autowired
	private AdministradorDao administradorDao;

	@Override
	public Administrador getAdministradorDesdeEmailYPassword(String email, String password) throws ObjectNotFoundinDBException {
		Administrador administrador =administradorDao.getAdministradorDesdeEmailYPassword(email, password);
		if (administrador == null) {
			throw new ObjectNotFoundinDBException();
		}
		return administrador;
	}
	

}
