package ar.edu.unq.inscripcionunq.spring.controller;

import java.util.List;

import org.apache.commons.mail.EmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ar.edu.unq.inscripcionunq.spring.controller.miniobject.ExceptionJson;
import ar.edu.unq.inscripcionunq.spring.controller.miniobject.UsuarioJson;
import ar.edu.unq.inscripcionunq.spring.exception.EmailInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.EncryptionDecryptionAESException;
import ar.edu.unq.inscripcionunq.spring.exception.UsuarioNoExisteException;
import ar.edu.unq.inscripcionunq.spring.exception.ExisteUsuarioConElMismoEmailException;
import ar.edu.unq.inscripcionunq.spring.exception.IdNumberFormatException;
import ar.edu.unq.inscripcionunq.spring.exception.ObjectNotFoundinDBException;
import ar.edu.unq.inscripcionunq.spring.exception.PasswordInvalidoException;
import ar.edu.unq.inscripcionunq.spring.service.UsuarioService;

@RestController
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioServiceImp;
	
	@PostMapping("/usuarios/ingresoUsuario")
	public ResponseEntity ingresoUsuario(@RequestBody UsuarioJson usuarioJson){
		try {
			return ResponseEntity.ok().body(usuarioServiceImp.ingresarUsuario(usuarioJson));
		} catch (ObjectNotFoundinDBException e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
					.body(new ExceptionJson(new UsuarioNoExisteException()));
		} catch (PasswordInvalidoException e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
					.body(new ExceptionJson(new PasswordInvalidoException()));
		} catch (EncryptionDecryptionAESException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		} 
	}
	
	@GetMapping("/usuarios")
	public ResponseEntity<List> getPeridos() {
		return ResponseEntity.ok().body(usuarioServiceImp.getUsuariosJson());
	}
	
	@PutMapping("/usuarios/nuevoUsuario")
	public ResponseEntity nuevoUsuario(@RequestBody UsuarioJson usuarioJson){
		try {
			usuarioServiceImp.crearUsuario(usuarioJson);
		} catch (EmailInvalidoException | ExisteUsuarioConElMismoEmailException e){
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
					.body(new ExceptionJson(e));
		} catch (EncryptionDecryptionAESException | EmailException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("/usuarios/eliminarUsuario/{idUsuario}")
	public ResponseEntity eliminarUsuario(@PathVariable String idUsuario) {
		try {
			usuarioServiceImp.eliminarUsuario(idUsuario);
		} catch (IdNumberFormatException | UsuarioNoExisteException e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ExceptionJson(e));
		}
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/usuarios/actualizarPassword")
	public ResponseEntity actualizarPassword(@RequestBody UsuarioJson usuarioJson){
			try {
				usuarioServiceImp.actualizarPassword(usuarioJson);
			} catch (UsuarioNoExisteException | PasswordInvalidoException e) {
				return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ExceptionJson(e));
			}
		
		return ResponseEntity.ok().build();
	}

}
