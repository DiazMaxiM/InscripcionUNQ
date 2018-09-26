package ar.edu.unq.inscripcionunq.spring.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unq.inscripcionunq.spring.controller.miniobject.ExceptionJson;
import ar.edu.unq.inscripcionunq.spring.exception.CarreraNoExisteException;
import ar.edu.unq.inscripcionunq.spring.exception.IdNumberFormatException;
import ar.edu.unq.inscripcionunq.spring.service.CarreraService;

@RestController
public class CarreraController {
	
	@Autowired
	private CarreraService carreraServiceImp;

	@GetMapping("/carreras")
	public ResponseEntity<List> getCarreras() {
		return ResponseEntity.ok().body(carreraServiceImp.list());
		
	}
	
	@DeleteMapping("/carreras/eliminarCarrera/{idCarrera}")
	public ResponseEntity eliminarCarrera(@PathVariable String idCarrera) {
		try {
			carreraServiceImp.eliminarCarrera(idCarrera);
		}
		catch (IdNumberFormatException | CarreraNoExisteException e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ExceptionJson(e));
		}
		return ResponseEntity.ok().build();


	}
	

}