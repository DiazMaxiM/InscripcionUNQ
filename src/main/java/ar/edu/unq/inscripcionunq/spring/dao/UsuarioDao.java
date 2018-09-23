package ar.edu.unq.inscripcionunq.spring.dao;

import ar.edu.unq.inscripcionunq.spring.model.Usuario;

public interface UsuarioDao extends GenericDao<Usuario> {
	
	Usuario obtenerUsuarioDesdeEmail(String email);

}
