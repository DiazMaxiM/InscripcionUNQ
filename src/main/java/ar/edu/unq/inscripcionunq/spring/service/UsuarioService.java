package ar.edu.unq.inscripcionunq.spring.service;

import ar.edu.unq.inscripcionunq.spring.exception.EmailInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.ExisteUsuarioConElMismoEmailException;
import ar.edu.unq.inscripcionunq.spring.exception.IdNumberFormatException;
import ar.edu.unq.inscripcionunq.spring.exception.ObjectNotFoundinDBException;
import ar.edu.unq.inscripcionunq.spring.exception.PasswordInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.UsuarioNoExisteException;
import ar.edu.unq.inscripcionunq.spring.model.Usuario;

public interface UsuarioService extends GenericService<Usuario>{
   
	void verificarSiExisteUsuario(String email, String password) throws ObjectNotFoundinDBException, PasswordInvalidoException;

	void crearUsuario(Usuario usuario) throws EmailInvalidoException,ExisteUsuarioConElMismoEmailException, PasswordInvalidoException;

	void eliminarUsuario(String idUsuario) throws UsuarioNoExisteException, IdNumberFormatException;
}
