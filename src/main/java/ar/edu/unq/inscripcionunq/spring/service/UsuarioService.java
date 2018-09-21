package ar.edu.unq.inscripcionunq.spring.service;

import ar.edu.unq.inscripcionunq.spring.exception.ObjectNotFoundinDBException;
import ar.edu.unq.inscripcionunq.spring.exception.PasswordInvalidoException;
import ar.edu.unq.inscripcionunq.spring.model.Usuario;

public interface UsuarioService extends GenericService<Usuario>{
   
	void verificarSiExisteUsuario(String email, String password) throws ObjectNotFoundinDBException, PasswordInvalidoException;
}
