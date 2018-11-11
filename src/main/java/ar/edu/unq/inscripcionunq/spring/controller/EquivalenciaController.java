package ar.edu.unq.inscripcionunq.spring.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unq.inscripcionunq.spring.controller.miniobject.EquivalenciaJson;
import ar.edu.unq.inscripcionunq.spring.controller.miniobject.ExceptionJson;
import ar.edu.unq.inscripcionunq.spring.exception.ObjectoNoEncontradoEnBDException;
import ar.edu.unq.inscripcionunq.spring.service.EquivalenciaService;

@RestController
public class EquivalenciaController {

	@Autowired
	private EquivalenciaService equivalenciaServiceImp;

	@GetMapping("/equivalencias")
	public ResponseEntity<List> getEquivalencia() {
		return ResponseEntity.ok().body(equivalenciaServiceImp.getEquivalencias().stream()
				.map(equivalencia -> new EquivalenciaJson(equivalencia)).collect(Collectors.toList()));
	}

	@PutMapping("/equivalencia")
	public ResponseEntity agregarEquivalencia(@RequestBody EquivalenciaJson equivalenciaJson) {
		try {
			equivalenciaServiceImp.agregarNuevaCarrera(equivalenciaJson);
		} catch (ObjectoNoEncontradoEnBDException exception) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ExceptionJson(exception));
		}
		return ResponseEntity.ok().build();
	}

	@PostMapping("/equivalencia")
	public ResponseEntity actualizarEquivalencia(@RequestBody EquivalenciaJson equivalenciaJson) {
		try {
			equivalenciaServiceImp.actualizarEquivalencia(equivalenciaJson);
		} catch (ObjectoNoEncontradoEnBDException exception) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ExceptionJson(exception));

		}
		return ResponseEntity.ok().build();
	}
}