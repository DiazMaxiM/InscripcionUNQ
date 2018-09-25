package ar.edu.unq.inscripcionunq.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unq.inscripcionunq.spring.controller.miniobject.ExceptionJson;
import ar.edu.unq.inscripcionunq.spring.exception.EmailUsuarioNoExisteException;
import ar.edu.unq.inscripcionunq.spring.exception.ObjectNotFoundinDBException;
import ar.edu.unq.inscripcionunq.spring.exception.PasswordInvalidoException;
import ar.edu.unq.inscripcionunq.spring.model.Usuario;
import ar.edu.unq.inscripcionunq.spring.service.UsuarioService;

@RestController
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioServiceImp;
	
	@PostMapping("/encuesta/ingresoUsuario")
	public ResponseEntity ingresoAdmistrador(@RequestBody Usuario usuarioJson){
		try {
			usuarioServiceImp.verificarSiExisteUsuario(usuarioJson.getEmail(), usuarioJson.getPassword());
		} catch (ObjectNotFoundinDBException e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
					.body(new ExceptionJson(new EmailUsuarioNoExisteException()));
		} catch (PasswordInvalidoException e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
					.body(new ExceptionJson(new PasswordInvalidoException()));
		} 
		return ResponseEntity.ok().body(null);
	}

}
