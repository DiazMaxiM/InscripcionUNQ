package ar.edu.unq.inscripcionunq.spring.controller;

import java.util.List;

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

import ar.edu.unq.inscripcionunq.spring.controller.miniobject.CarreraJson;
import ar.edu.unq.inscripcionunq.spring.controller.miniobject.EstudianteJson;
import ar.edu.unq.inscripcionunq.spring.controller.miniobject.ExceptionJson;
import ar.edu.unq.inscripcionunq.spring.controller.miniobject.MateriaSistemaJson;
import ar.edu.unq.inscripcionunq.spring.exception.ApellidoInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.CarreraNoExisteException;
import ar.edu.unq.inscripcionunq.spring.exception.CodigoInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.DescripcionInvalidaException;
import ar.edu.unq.inscripcionunq.spring.exception.EmailInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.EstadoInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.ExisteCarreraConElMismoCodigoException;
import ar.edu.unq.inscripcionunq.spring.exception.ExisteMateriaConElMismoCodigoException;
import ar.edu.unq.inscripcionunq.spring.exception.IdNumberFormatException;
import ar.edu.unq.inscripcionunq.spring.exception.MateriaNoExisteException;
import ar.edu.unq.inscripcionunq.spring.exception.NombreInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.ObjectNotFoundinDBException;
import ar.edu.unq.inscripcionunq.spring.exception.StudentNotExistenException;
import ar.edu.unq.inscripcionunq.spring.exception.IdNumberFormatException;
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
		return ResponseEntity.ok().body(materiaServiceImp.getMateriasJson());
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
	public ResponseEntity modificarMateria(@RequestBody MateriaSistemaJson materiaJson) throws IdNumberFormatException, MateriaNoExisteException, ExisteMateriaConElMismoCodigoException{
		try {
			materiaServiceImp.actualizarMateria(materiaJson);
		} catch (IdNumberFormatException e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ExceptionJson(e));
		} catch (MateriaNoExisteException e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ExceptionJson(e));
		}
		return ResponseEntity.ok().build();
	}
	
	@PutMapping("/materias/nuevaMateria")
	public ResponseEntity agregarNuevaMateria(@RequestBody MateriaSistemaJson materiaJson)throws DescripcionInvalidaException,
	CodigoInvalidoException, EstadoInvalidoException, ExisteMateriaConElMismoCodigoException, IdNumberFormatException, MateriaNoExisteException {
		try {
			materiaServiceImp.agregarNuevaMateria(materiaJson);
		} catch (DescripcionInvalidaException | CodigoInvalidoException | EstadoInvalidoException
				| ExisteMateriaConElMismoCodigoException e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ExceptionJson(e));
		}
		return ResponseEntity.ok().build();

	}
}
