package ar.edu.unq.inscripcionunq.spring.controller;

import java.util.Arrays;
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
import ar.edu.unq.inscripcionunq.spring.model.TipoPerfil;
import ar.edu.unq.inscripcionunq.spring.service.UsuarioService;

@RestController
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioServiceImp;

	@PostMapping("/usuarios/ingresoUsuario")
	public ResponseEntity ingresarUsuario(@RequestBody UsuarioJson usuarioJson) {
		try {
			return ResponseEntity.ok().body(usuarioServiceImp.ingresarUsuario(usuarioJson));
		} catch (UsuarioNoExisteException | PasswordInvalidoException | EncriptarDesencriptarAESException e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ExceptionJson(e));
		}
	}

	@GetMapping("/usuarios/{perfil}")
	public ResponseEntity getUsuario(@PathVariable String perfil) {
		try {
			return ResponseEntity.ok().body(usuarioServiceImp.getUsuariosSegunPerfil(perfil));
		} catch (PerfilInvalidoException e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ExceptionJson(e));
		}
	}

	@PutMapping("/usuarios/nuevoUsuario")
	public ResponseEntity nuevoUsuario(@RequestBody UsuarioJson usuarioJson) {
		try {
			usuarioServiceImp.crearUsuario(usuarioJson);
		} catch (EmailInvalidoException | ExisteUsuarioConElMismoEmailException | NombreInvalidoException
				| ApellidoInvalidoException | DniInvalidoException e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ExceptionJson(e));
		} catch (EmailException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/usuarios/eliminarUsuario/{idUsuario}")
	public ResponseEntity eliminarUsuario(@PathVariable String idUsuario) {
		try {
			usuarioServiceImp.eliminarUsuario(idUsuario);
		} catch (FormatoNumeroIdException | UsuarioNoExisteException e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ExceptionJson(e));
		}
		return ResponseEntity.ok().build();
	}

	@PostMapping("/usuarios/actualizarPassword")
	public ResponseEntity actualizarPassword(@RequestBody UsuarioJson usuarioJson) {
		try {
			usuarioServiceImp.actualizarPassword(usuarioJson);
		} catch (UsuarioNoExisteException | PasswordInvalidoException e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ExceptionJson(e));
		}

		return ResponseEntity.ok().build();
	}

	@PostMapping("/usuarios/recuperarPassword")
	public ResponseEntity recuperarPassword(@RequestBody UsuarioJson usuarioJson) {
		try {
			usuarioServiceImp.recuperarPassword(usuarioJson);
		} catch (EnvioMailException e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ExceptionJson(e));
		}

		return ResponseEntity.ok().build();
	}

	@PostMapping("/usuarios/actualizarUsuario")
	public ResponseEntity actualizarUsuario(@RequestBody UsuarioJson usuarioJson) {
		try {
			usuarioServiceImp.actualizarUsuario(usuarioJson);
		} catch (UsuarioNoExisteException | EmailInvalidoException | NombreInvalidoException | ApellidoInvalidoException
				| FormatoNumeroIdException | ExisteUsuarioConElMismoEmailException | DniInvalidoException e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ExceptionJson(e));
		}

		return ResponseEntity.ok().build();
	}

	@PostMapping("/usuarios/actualizarPerfiles/{idUsuario}")
	public ResponseEntity actualizarComisiones(@PathVariable String idUsuario, @RequestBody List<String> perfiles) {
		try {
			usuarioServiceImp.actualizarPerfiles(idUsuario, perfiles);
		} catch (PerfilInvalidoException | FormatoNumeroIdException | UsuarioNoExisteException e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ExceptionJson(e));
		}

		return ResponseEntity.ok().build();
	}

	@GetMapping("/tipoPerfiles")
	public ResponseEntity getTiposPerfiles() {
		TipoPerfil[] perfiles = TipoPerfil.values();
		Arrays.sort(perfiles);
		return ResponseEntity.ok().body(perfiles);
	}
}