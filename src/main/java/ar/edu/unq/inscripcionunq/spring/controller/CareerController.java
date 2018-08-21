package ar.edu.unq.inscripcionunq.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unq.inscripcionunq.spring.model.Career;
import ar.edu.unq.inscripcionunq.spring.service.CareerService;

@RestController
public class CareerController {

	@Autowired
	private CareerService careerService;

	@PostMapping("/career")
	public ResponseEntity<?> save(@RequestBody Career career) {
		long id = careerService.save(career);
		return ResponseEntity.ok().body("Nueva Carrera creada ID:" + id);
	}

	@GetMapping("/career/{id}")
	public ResponseEntity<Career> get(@PathVariable("id") long id) {
		Career career = careerService.get(id);
		return ResponseEntity.ok().body(career);
	}

	@GetMapping("/career")
	public ResponseEntity<List<Career>> list() {
		List<Career> careers = careerService.list();
		return ResponseEntity.ok().body(careers);
	}

	@PutMapping("/career/{id}")
	public ResponseEntity<?> update(@PathVariable("id") long id, @RequestBody Career career) {
		careerService.update(id, career);
		return ResponseEntity.ok().body("Carrera actualizada.");
	}

	@DeleteMapping("/career/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") long id) {
		careerService.delete(id);
		return ResponseEntity.ok().body("Carrera borrada");
	}
}