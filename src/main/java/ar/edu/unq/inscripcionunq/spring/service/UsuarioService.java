package ar.edu.unq.inscripcionunq.spring.service;

import java.util.List;

import org.apache.commons.mail.EmailException;

import ar.edu.unq.inscripcionunq.spring.controller.miniobject.UsuarioJson;
import ar.edu.unq.inscripcionunq.spring.exception.ApellidoInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.DniInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.EmailInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.EncriptarDesencriptarAESException;
import ar.edu.unq.inscripcionunq.spring.exception.EnvioMailException;
import ar.edu.unq.inscripcionunq.spring.exception.ExisteUsuarioConElMismoEmailException;
import ar.edu.unq.inscripcionunq.spring.exception.FormatoNumeroIdException;
import ar.edu.unq.inscripcionunq.spring.exception.NombreInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.PasswordInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.PerfilInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.UsuarioNoExisteException;
import ar.edu.unq.inscripcionunq.spring.model.Usuario;

public interface UsuarioService extends GenericService<Usuario> {

	void crearUsuario(UsuarioJson usuario) throws EmailInvalidoException, NombreInvalidoException,
			ApellidoInvalidoException, EmailException, ExisteUsuarioConElMismoEmailException, DniInvalidoException;

	void eliminarUsuario(String idUsuario) throws UsuarioNoExisteException, FormatoNumeroIdException;

	UsuarioJson ingresarUsuario(UsuarioJson usuarioJson)
			throws UsuarioNoExisteException, PasswordInvalidoException, EncriptarDesencriptarAESException;

	void actualizarPassword(UsuarioJson usuarioJson) throws UsuarioNoExisteException, PasswordInvalidoException;

	List<UsuarioJson> getUsuariosSegunPerfil(String perfil) throws PerfilInvalidoException;

	void actualizarUsuario(UsuarioJson usuarioJson)
			throws UsuarioNoExisteException, EmailInvalidoException, NombreInvalidoException, ApellidoInvalidoException,
			FormatoNumeroIdException, ExisteUsuarioConElMismoEmailException, DniInvalidoException;

	void actualizarPerfiles(String idUsuario, List<String> perfiles)
			throws PerfilInvalidoException, FormatoNumeroIdException, UsuarioNoExisteException;

	void recuperarPassword(UsuarioJson usuarioJson) throws EnvioMailException, UsuarioNoExisteException;
}