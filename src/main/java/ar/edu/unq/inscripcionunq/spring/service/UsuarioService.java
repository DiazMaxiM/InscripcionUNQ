package ar.edu.unq.inscripcionunq.spring.service;

import java.util.List;

import org.apache.commons.mail.EmailException;

import ar.edu.unq.inscripcionunq.spring.controller.miniobject.UsuarioJson;
import ar.edu.unq.inscripcionunq.spring.exception.EmailInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.EncryptionDecryptionAESException;
import ar.edu.unq.inscripcionunq.spring.exception.ExisteUsuarioConElMismoEmailException;
import ar.edu.unq.inscripcionunq.spring.exception.IdNumberFormatException;
import ar.edu.unq.inscripcionunq.spring.exception.ObjectNotFoundinDBException;
import ar.edu.unq.inscripcionunq.spring.exception.PasswordInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.PerfilInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.UsuarioNoExisteException;
import ar.edu.unq.inscripcionunq.spring.model.Usuario;

public interface UsuarioService extends GenericService<Usuario>{
   
	void crearUsuario(UsuarioJson usuario) throws EmailInvalidoException,ExisteUsuarioConElMismoEmailException,EncryptionDecryptionAESException, EmailException;

	void eliminarUsuario(String idUsuario) throws UsuarioNoExisteException, IdNumberFormatException;

	UsuarioJson ingresarUsuario(UsuarioJson usuarioJson) throws UsuarioNoExisteException, PasswordInvalidoException, EncryptionDecryptionAESException;

	void actualizarPassword(UsuarioJson usuarioJson) throws UsuarioNoExisteException, PasswordInvalidoException;

	List<UsuarioJson> getUsuariosSegunPerfil(String perfil) throws PerfilInvalidoException;
}
