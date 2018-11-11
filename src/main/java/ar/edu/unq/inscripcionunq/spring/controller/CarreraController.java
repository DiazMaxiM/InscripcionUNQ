package ar.edu.unq.inscripcionunq.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unq.inscripcionunq.spring.controller.miniobject.CarreraJson;
import ar.edu.unq.inscripcionunq.spring.controller.miniobject.ExceptionJson;
import ar.edu.unq.inscripcionunq.spring.exception.CarreraNoExisteException;
import ar.edu.unq.inscripcionunq.spring.exception.CodigoInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.DescripcionInvalidaException;
import ar.edu.unq.inscripcionunq.spring.exception.EstadoInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.ExisteCarreraConElMismoCodigoException;
import ar.edu.unq.inscripcionunq.spring.service.CarreraService;

@RestController
public class CarreraController {
	
	@Autowired
	private CarreraService carreraServiceImp;

	@GetMapping("/carreras")
	public ResponseEntity<List> getCarreras() {
		return ResponseEntity.ok().body(carreraServiceImp.getCarrerasJson());
	}
	
	@PutMapping("/carreras/nuevaCarrera")
	public ResponseEntity agregarNuevaCarrera(@RequestBody CarreraJson carreraJson) {
		try {
			carreraServiceImp.agregarNuevaCarrera(carreraJson);
		} catch (DescripcionInvalidaException | CodigoInvalidoException | EstadoInvalidoException
				| ExisteCarreraConElMismoCodigoException e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ExceptionJson(e));
		}
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/carreras/actualizarCarrera")
	public ResponseEntity actualizarCarrera(@RequestBody CarreraJson carreraJson) {
		try {
			carreraServiceImp.actualizarCarrera(carreraJson);
		} catch (DescripcionInvalidaException | CodigoInvalidoException | EstadoInvalidoException
				| ExisteCarreraConElMismoCodigoException | CarreraNoExisteException e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ExceptionJson(e));
		}
		return ResponseEntity.ok().build();
	}
}