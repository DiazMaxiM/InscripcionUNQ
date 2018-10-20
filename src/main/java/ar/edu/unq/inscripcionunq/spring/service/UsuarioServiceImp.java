package ar.edu.unq.inscripcionunq.spring.service;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.inscripcionunq.spring.dao.UsuarioDao;
import ar.edu.unq.inscripcionunq.spring.exception.EmailInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.ExisteUsuarioConElMismoEmailException;
import ar.edu.unq.inscripcionunq.spring.exception.IdNumberFormatException;
import ar.edu.unq.inscripcionunq.spring.exception.ObjectNotFoundinDBException;
import ar.edu.unq.inscripcionunq.spring.exception.PasswordInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.UsuarioNoExisteException;
import ar.edu.unq.inscripcionunq.spring.model.Usuario;
import ar.edu.unq.inscripcionunq.spring.validacion.Validacion;

@Service
@Transactional
public class UsuarioServiceImp extends GenericServiceImp<Usuario> implements UsuarioService {
    
	@Autowired
	private UsuarioDao usuarioDao;

	@Override
	public void verificarSiExisteUsuario(String email,String password) throws ObjectNotFoundinDBException, PasswordInvalidoException {
		
		Usuario usuario = usuarioDao.obtenerUsuarioDesdeEmail(email);
		if (usuario == null) {
			throw new ObjectNotFoundinDBException();
		}else {
		   usuario.validarPassword(password);
		}
		
	}
	
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void crearUsuario(Usuario usuario) throws EmailInvalidoException, ExisteUsuarioConElMismoEmailException, PasswordInvalidoException {
		Validacion.validarUsuario(usuario);
		try {
			this.save(usuario);
		} catch (ConstraintViolationException e) {
		    throw new ExisteUsuarioConElMismoEmailException();
		}
		
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
			

}
