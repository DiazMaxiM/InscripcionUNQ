package ar.edu.unq.inscripcionunq.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.inscripcionunq.spring.dao.UsuarioDao;
import ar.edu.unq.inscripcionunq.spring.exception.ObjectNotFoundinDBException;
import ar.edu.unq.inscripcionunq.spring.exception.PasswordInvalidoException;
import ar.edu.unq.inscripcionunq.spring.model.Usuario;

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
			

}
