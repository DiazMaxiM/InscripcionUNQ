package ar.edu.unq.inscripcionunq.spring.service;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.mail.EmailException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.inscripcionunq.spring.controller.miniobject.UsuarioJson;
import ar.edu.unq.inscripcionunq.spring.dao.UsuarioDao;
import ar.edu.unq.inscripcionunq.spring.exception.EmailInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.EncryptionDecryptionAESException;
import ar.edu.unq.inscripcionunq.spring.exception.ExisteUsuarioConElMismoEmailException;
import ar.edu.unq.inscripcionunq.spring.exception.IdNumberFormatException;
import ar.edu.unq.inscripcionunq.spring.exception.ObjectNotFoundinDBException;
import ar.edu.unq.inscripcionunq.spring.exception.PasswordInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.UsuarioNoExisteException;
import ar.edu.unq.inscripcionunq.spring.model.Mail;
import ar.edu.unq.inscripcionunq.spring.model.Usuario;
import ar.edu.unq.inscripcionunq.spring.validacion.Validacion;

@Service
@Transactional
public class UsuarioServiceImp extends GenericServiceImp<Usuario> implements UsuarioService {
    
	@Autowired
	private UsuarioDao usuarioDao;
	
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void crearUsuario(UsuarioJson usuarioJson) throws EmailInvalidoException, ExisteUsuarioConElMismoEmailException,EncryptionDecryptionAESException, EmailException {
		Validacion.validarEmail(usuarioJson.email);
		String password =  RandomStringUtils.random(8, 0, 20, true, true, "qw32rfHIJk9iQ8Ud7h0X".toCharArray());
		Usuario usuario = new Usuario(usuarioJson.email, password);
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
	public List<UsuarioJson> getUsuariosJson() {
		List <Usuario> usuarios = this.list();
		return usuarios.stream().map(u -> new UsuarioJson(u)).collect(Collectors.toList());
	}

	@Override
	public Long ingresarUsuario(UsuarioJson usuarioJson) throws ObjectNotFoundinDBException, PasswordInvalidoException, EncryptionDecryptionAESException {
		Usuario usuario = usuarioDao.obtenerUsuarioDesdeEmail(usuarioJson.email);
		if (usuario == null) {
			throw new ObjectNotFoundinDBException();
		}else {
		   usuario.validarPassword(usuarioJson.password);
		}
		return usuario.getId();
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
	
	
			

}
