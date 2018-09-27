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
import ar.edu.unq.inscripcionunq.spring.exception.MateriaNoExisteException;
import ar.edu.unq.inscripcionunq.spring.service.MateriaService;

@RestController
public class MateriaController {
	@Autowired
	private MateriaService materiaServiceImp;


	@GetMapping("/materias")
	public ResponseEntity<List> getMaterias() {
		return ResponseEntity.ok().body(materiaServiceImp.list());
	}
	
	@DeleteMapping("/materias/eliminarMateria/{idMateria}")
	public ResponseEntity eliminarMateria(@PathVariable String idMateria) {
		try {
			materiaServiceImp.eliminarMateria(idMateria);
		}
		catch (IdNumberFormatException | MateriaNoExisteException e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ExceptionJson(e));
		}
		return ResponseEntity.ok().build();


	}
}
