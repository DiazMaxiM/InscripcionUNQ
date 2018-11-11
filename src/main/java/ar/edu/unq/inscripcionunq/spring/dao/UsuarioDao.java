package ar.edu.unq.inscripcionunq.spring.dao;

import java.util.List;

import ar.edu.unq.inscripcionunq.spring.model.TipoPerfil;
import ar.edu.unq.inscripcionunq.spring.model.Usuario;

public interface UsuarioDao extends GenericDao<Usuario> {
	
	Usuario obtenerUsuarioDesdeEmail(String email);

	List<Usuario> obtenerUsuariosConPerfil(TipoPerfil perfil);
}