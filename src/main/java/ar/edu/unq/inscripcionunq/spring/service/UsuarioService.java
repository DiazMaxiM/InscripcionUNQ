package ar.edu.unq.inscripcionunq.spring.service;

import java.util.List;

import ar.edu.unq.inscripcionunq.spring.controller.miniobject.UsuarioJson;
import ar.edu.unq.inscripcionunq.spring.exception.EmailInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.EncryptionDecryptionAESException;
import ar.edu.unq.inscripcionunq.spring.exception.ExisteUsuarioConElMismoEmailException;
import ar.edu.unq.inscripcionunq.spring.exception.IdNumberFormatException;
import ar.edu.unq.inscripcionunq.spring.exception.ObjectNotFoundinDBException;
import ar.edu.unq.inscripcionunq.spring.exception.PasswordInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.UsuarioNoExisteException;
import ar.edu.unq.inscripcionunq.spring.model.Usuario;

public interface UsuarioService extends GenericService<Usuario>{
   
	void verificarSiExisteUsuario(String email, String password) throws ObjectNotFoundinDBException, PasswordInvalidoException, EncryptionDecryptionAESException;

	void crearUsuario(UsuarioJson usuario) throws EmailInvalidoException,ExisteUsuarioConElMismoEmailException, PasswordInvalidoException, EncryptionDecryptionAESException;

	void eliminarUsuario(String idUsuario) throws UsuarioNoExisteException, IdNumberFormatException;

	List<UsuarioJson> getUsuariosJson();
}
