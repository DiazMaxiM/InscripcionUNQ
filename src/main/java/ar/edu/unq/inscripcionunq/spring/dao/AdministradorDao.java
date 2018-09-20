package ar.edu.unq.inscripcionunq.spring.dao;

import ar.edu.unq.inscripcionunq.spring.model.Administrador;

public interface AdministradorDao extends GenericDao<Administrador> {
	
	Administrador getAdministradorDesdeEmailYPassword(String email, String password);

}
