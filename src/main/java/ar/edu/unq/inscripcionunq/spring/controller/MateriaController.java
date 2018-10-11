package ar.edu.unq.inscripcionunq.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jcabi.aspects.Loggable;

import ar.edu.unq.inscripcionunq.spring.controller.miniobject.ExceptionJson;
import ar.edu.unq.inscripcionunq.spring.controller.miniobject.MateriaSistemaJson;
import ar.edu.unq.inscripcionunq.spring.exception.CodigoInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.DescripcionInvalidaException;
import ar.edu.unq.inscripcionunq.spring.exception.EstadoInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.ExisteMateriaConElMismoCodigoException;
import ar.edu.unq.inscripcionunq.spring.exception.HorarioInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.IdNumberFormatException;
import ar.edu.unq.inscripcionunq.spring.exception.MateriaNoExisteException;
import ar.edu.unq.inscripcionunq.spring.exception.NombreInvalidoException;
import ar.edu.unq.inscripcionunq.spring.service.MateriaService;

@RestController
public class MateriaController {
	@Autowired
	private MateriaService materiaServiceImp;

	@GetMapping("/materias")
	@Loggable
	public ResponseEntity<List> getMaterias() {
		return ResponseEntity.ok().body(materiaServiceImp.getMateriasJson());
	}
	
	@PostMapping("/materias/modificarMateria")
	@Loggable
	public ResponseEntity modificarMateria(@RequestBody MateriaSistemaJson materiaJson) throws 
	IdNumberFormatException, MateriaNoExisteException, ExisteMateriaConElMismoCodigoException, 
	CodigoInvalidoException, NombreInvalidoException, EstadoInvalidoException, DescripcionInvalidaException, 
	HorarioInvalidoException{
		try {
			materiaServiceImp.actualizarMateria(materiaJson);
		} catch (IdNumberFormatException | MateriaNoExisteException | ExisteMateriaConElMismoCodigoException | 
				CodigoInvalidoException | NombreInvalidoException | EstadoInvalidoException | DescripcionInvalidaException | 
				HorarioInvalidoException e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ExceptionJson(e));
		}
		return ResponseEntity.ok().build();
	}
	@Loggable
	@PutMapping("/materias/nuevaMateria")
	public ResponseEntity agregarNuevaMateria(@RequestBody MateriaSistemaJson materiaJson)throws DescripcionInvalidaException,
	CodigoInvalidoException, EstadoInvalidoException, ExisteMateriaConElMismoCodigoException, IdNumberFormatException, MateriaNoExisteException {
		try {
			materiaServiceImp.agregarNuevaMateria(materiaJson);
		} catch (DescripcionInvalidaException | CodigoInvalidoException | EstadoInvalidoException | 
				ExisteMateriaConElMismoCodigoException | IdNumberFormatException | MateriaNoExisteException e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ExceptionJson(e));
		}
		return ResponseEntity.ok().build();
	}
	@Loggable
	@GetMapping("/oferta-academica/materias/{idCarrera}")
	public ResponseEntity getMateriasParaCarrera(@PathVariable String idCarrera) throws IdNumberFormatException {
		return ResponseEntity.ok().body(materiaServiceImp.getMateriasParaCarrera(idCarrera));
	}
}