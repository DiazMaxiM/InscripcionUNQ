package ar.edu.unq.inscripcionunq.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unq.inscripcionunq.spring.controller.miniobject.EstudianteJson;
import ar.edu.unq.inscripcionunq.spring.controller.miniobject.ExceptionJson;
import ar.edu.unq.inscripcionunq.spring.controller.miniobject.MateriaJson;
import ar.edu.unq.inscripcionunq.spring.exception.ApellidoInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.CarreraNoExisteException;
import ar.edu.unq.inscripcionunq.spring.exception.EmailInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.IdNumberFormatException;
import ar.edu.unq.inscripcionunq.spring.exception.MateriaNoExisteException;
import ar.edu.unq.inscripcionunq.spring.exception.NombreInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.ObjectNotFoundinDBException;
import ar.edu.unq.inscripcionunq.spring.exception.StudentNotExistenException;
import ar.edu.unq.inscripcionunq.spring.model.Estudiante;
import ar.edu.unq.inscripcionunq.spring.model.Materia;
import ar.edu.unq.inscripcionunq.spring.service.EstudianteService;
import ar.edu.unq.inscripcionunq.spring.service.MateriaService;
import ar.edu.unq.inscripcionunq.spring.service.MateriaServiceImp;

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
	
	@PostMapping("/materias/modificarMateria")
	public ResponseEntity modificarMateria(@RequestBody MateriaJson materiaJson) throws NombreInvalidoException{
		Materia materiaRecived = new Materia(materiaJson.codigo, materiaJson.nombre, materiaJson.horas, materiaJson.carreras);
		Materia materia;
		try {
			materia = materiaServiceImp.get(materiaJson.id);
			materia.update(materiaRecived);
			materiaServiceImp.update(materia);
		} catch (ObjectNotFoundinDBException e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
					.body(new ExceptionJson(new StudentNotExistenException()));
		}
		return ResponseEntity.ok().body(null);
	}
}
