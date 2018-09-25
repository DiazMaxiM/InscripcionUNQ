package ar.edu.unq.inscripcionunq.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unq.inscripcionunq.spring.service.CarreraService;

@RestController
public class CarreraController {
	
	@Autowired
	private CarreraService carreraServiceImp;

	@GetMapping("/carreras")
	public ResponseEntity<List> getCarreras() {
			return ResponseEntity.ok().body(carreraServiceImp.list());
		
	}
}