package ar.edu.unq.inscripcionunq.spring.service;

import ar.edu.unq.inscripcionunq.spring.exception.ObjectNotFoundinDBException;
import ar.edu.unq.inscripcionunq.spring.model.Administrador;

public interface AdministradorService extends GenericService<Administrador>{
   
	Administrador getAdministradorDesdeEmailYPassword(String email, String password)throws ObjectNotFoundinDBException;
}
