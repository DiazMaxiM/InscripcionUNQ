package ar.edu.unq.inscripcionunq.spring.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.mail.EmailException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.inscripcionunq.spring.controller.miniobject.UsuarioJson;
import ar.edu.unq.inscripcionunq.spring.dao.EncuestaDao;
import ar.edu.unq.inscripcionunq.spring.dao.UsuarioDao;
import ar.edu.unq.inscripcionunq.spring.exception.ApellidoInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.EmailInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.EncryptionDecryptionAESException;
import ar.edu.unq.inscripcionunq.spring.exception.ExisteUsuarioConElMismoEmailException;
import ar.edu.unq.inscripcionunq.spring.exception.IdNumberFormatException;
import ar.edu.unq.inscripcionunq.spring.exception.MateriaNoExisteException;
import ar.edu.unq.inscripcionunq.spring.exception.NombreInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.ObjectNotFoundinDBException;
import ar.edu.unq.inscripcionunq.spring.exception.PasswordInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.PerfilInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.UsuarioNoExisteException;
import ar.edu.unq.inscripcionunq.spring.model.Estudiante;
import ar.edu.unq.inscripcionunq.spring.model.Mail;
import ar.edu.unq.inscripcionunq.spring.model.Materia;
import ar.edu.unq.inscripcionunq.spring.model.TipoPerfil;
import ar.edu.unq.inscripcionunq.spring.model.Usuario;
import ar.edu.unq.inscripcionunq.spring.validacion.Validacion;

@Service
@Transactional
public class UsuarioServiceImp extends GenericServiceImp<Usuario> implements UsuarioService {
    
	@Autowired
	private UsuarioDao usuarioDao;
	@Autowired
	private EncuestaDao encuestaDao;
	
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void crearUsuario(UsuarioJson usuarioJson) throws EmailInvalidoException, NombreInvalidoException, ApellidoInvalidoException, EmailException, ExisteUsuarioConElMismoEmailException{
		
		String password =  RandomStringUtils.random(8, 0, 20, true, true, "qw32rfHIJk9iQ8Ud7h0X".toCharArray());
		Usuario usuario = this.mapearUsuarioDesdeJson(usuarioJson);
		usuario.setPassword(password);
		usuario.agregarPerfil(TipoPerfil.ADMINISTRADOR);
		Validacion.validarUsuario(usuario);
		try {
			this.save(usuario);
			enviarMail(usuario);
		} catch (ConstraintViolationException e) {
		    throw new ExisteUsuarioConElMismoEmailException();
		}
		
	}

	private void enviarMail(Usuario usuario) throws EmailException {
		Mail mail = new Mail();
		mail.send(usuario.getEmail(),"[Encuesta de pre inscripción] Alta Usuario",
				"Estimado, Te enviamos tu contraseña: "+ usuario.getPassword()+ ". La misma puede ser modificada una vez ingresado a la aplicacion.");
	}

	@Override
	public void eliminarUsuario(String idUsuario) throws UsuarioNoExisteException, IdNumberFormatException {
			try {
				Usuario usuario = this.get(new Long(idUsuario));
				this.delete(usuario);
			} catch (NumberFormatException e) {
				throw new IdNumberFormatException();
			} catch (ObjectNotFoundinDBException e) {
				throw new UsuarioNoExisteException();
			}
		
	}

	@Override
	public UsuarioJson ingresarUsuario(UsuarioJson usuarioJson) throws UsuarioNoExisteException, PasswordInvalidoException, EncryptionDecryptionAESException{
		Usuario usuario = usuarioDao.obtenerUsuarioDesdeEmail(usuarioJson.email);
		if (usuario == null) {
			usuario = crearUsuarioDesdeEstudiante(usuarioJson.email);
		}
		usuario.validarPassword(usuarioJson.password);
		return new UsuarioJson(usuario);
	}

	@Override
	public void actualizarPassword(UsuarioJson usuarioJson) throws UsuarioNoExisteException, PasswordInvalidoException {
		try {
			Usuario usuario = this.get(usuarioJson.id);
			Validacion.validarPassword(usuarioJson.password);
			usuario.setPassword(usuarioJson.password);
			this.update(usuario);
		} catch (ObjectNotFoundinDBException e) {
			throw new UsuarioNoExisteException();
		}
		
		
	}
	
	private Usuario crearUsuarioDesdeEstudiante(String email) throws UsuarioNoExisteException {
		Estudiante estudiante = encuestaDao.getDatosDeUsuarioDesdeEncuesta(email);
		Usuario usuario;
		usuario = new Usuario(estudiante.getNombre(),estudiante.getApellido(),email);
	    usuario.setPassword(estudiante.getDni());
		usuario.setDni(estudiante.getDni());
		usuario.agregarPerfil(TipoPerfil.ESTUDIANTE);
		this.save(usuario);	
		return usuario;
	}

	@Override
	public List<UsuarioJson> getUsuariosSegunPerfil(String perfil) throws PerfilInvalidoException {
		Validacion.validarPerfil(perfil);
		TipoPerfil tipoPerfil = TipoPerfil.valueOf(perfil);
		List <Usuario> usuarios = usuarioDao.obtenerUsuariosConPerfil(tipoPerfil);
		return usuarios.stream().map(u -> new UsuarioJson(u)).collect(Collectors.toList());
	}

	@Override
	public void actualizarUsuario(UsuarioJson usuarioJson) throws UsuarioNoExisteException, EmailInvalidoException, NombreInvalidoException, ApellidoInvalidoException, IdNumberFormatException, ExisteUsuarioConElMismoEmailException {
		
		Usuario usuarioActualizado = this.mapearUsuarioDesdeJson(usuarioJson);
		Validacion.validarUsuario(usuarioActualizado);
		try {
			Usuario usuarioOriginal = this.get(usuarioJson.id);
			
			if(!usuarioOriginal.getEmail().equals(usuarioActualizado.getEmail())) {
				this.validarSiExisteUsuarioConMismoEmail(usuarioActualizado.getEmail());
			}
			usuarioOriginal.actualizarDatos(usuarioActualizado);
			this.save(usuarioOriginal);
		} catch (NumberFormatException e) {
			throw new IdNumberFormatException();
		} catch (ObjectNotFoundinDBException e) {
			throw new UsuarioNoExisteException();
		}
		
	}

	private void validarSiExisteUsuarioConMismoEmail(String email) throws ExisteUsuarioConElMismoEmailException {
		Usuario usuario = usuarioDao.obtenerUsuarioDesdeEmail(email);
		if (usuario == null) {
			try {
				encuestaDao.getDatosDeUsuarioDesdeEncuesta(email);
				throw new ExisteUsuarioConElMismoEmailException();
			} catch (UsuarioNoExisteException e) {
				
			}
		}else {
			throw new ExisteUsuarioConElMismoEmailException();
		}
		
	}

	private Usuario mapearUsuarioDesdeJson(UsuarioJson usuarioJson) {
		return new Usuario(usuarioJson.nombre,usuarioJson.apellido,usuarioJson.email);
	}

	@Override
	public void actualizarPerfiles(String idUsuario, List<String> perfiles) throws PerfilInvalidoException, IdNumberFormatException, UsuarioNoExisteException {
		Validacion.validarPerfiles(perfiles);
		try {
			Usuario usuario = this.get(new Long(idUsuario));
			List<TipoPerfil> perfilesActual = crearListaDePerfiles(perfiles);
			usuario.setPerfiles(perfilesActual);
			this.save(usuario);
		} catch (NumberFormatException e) {
			throw new IdNumberFormatException();
		} catch (ObjectNotFoundinDBException e) {
			throw new UsuarioNoExisteException();
		}
		
	}

	private List<TipoPerfil> crearListaDePerfiles(List<String> perfiles) {
		List<TipoPerfil> perfilesActual = new ArrayList<>();
		for(String perfil : perfiles) {
			perfilesActual.add(TipoPerfil.valueOf(perfil));
		}
		return perfilesActual;
	}
	
			

}
