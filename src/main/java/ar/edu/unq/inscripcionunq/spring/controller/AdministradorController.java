package ar.edu.unq.inscripcionunq.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unq.inscripcionunq.spring.controller.miniobject.ExceptionJson;
import ar.edu.unq.inscripcionunq.spring.exception.AdmistradorNoExisteException;
import ar.edu.unq.inscripcionunq.spring.exception.ApellidoInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.EmailInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.NombreInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.ObjectNotFoundinDBException;
import ar.edu.unq.inscripcionunq.spring.model.Administrador;
import ar.edu.unq.inscripcionunq.spring.service.AdministradorService;

@RestController
public class AdministradorController {
	
	@Autowired
	private AdministradorService administradorServiceImp;
	
	@PostMapping("/administracion/ingresoAdmistrador")
	public ResponseEntity ingresoAdmistrador(@RequestBody Administrador administradorJson) throws NombreInvalidoException, ApellidoInvalidoException, EmailInvalidoException {
		Administrador administradorRecibido = new Administrador(administradorJson.getEmail(),administradorJson.getPassword());
		 Administrador administrador;
		try {
			administrador = administradorServiceImp.getAdministradorDesdeEmailYPassword(administradorRecibido.getEmail(), administradorRecibido.getPassword());
		} catch (ObjectNotFoundinDBException e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
					.body(new ExceptionJson(new AdmistradorNoExisteException()));
		}
		return ResponseEntity.ok().body(null);
	}

}
