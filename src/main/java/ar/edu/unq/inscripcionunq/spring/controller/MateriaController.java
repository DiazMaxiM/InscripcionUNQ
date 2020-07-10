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

import ar.edu.unq.inscripcionunq.spring.controller.miniobject.ExceptionJson;
import ar.edu.unq.inscripcionunq.spring.controller.miniobject.IdJson;
import ar.edu.unq.inscripcionunq.spring.controller.miniobject.MateriaSistemaJson;
import ar.edu.unq.inscripcionunq.spring.exception.CodigoInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.DescripcionInvalidaException;
import ar.edu.unq.inscripcionunq.spring.exception.EstadoInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.ExisteMateriaConElMismoCodigoException;
import ar.edu.unq.inscripcionunq.spring.exception.FormatoNumeroIdException;
import ar.edu.unq.inscripcionunq.spring.exception.HorarioInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.MateriaNoExisteException;
import ar.edu.unq.inscripcionunq.spring.exception.NombreInvalidoException;
import ar.edu.unq.inscripcionunq.spring.service.MateriaService;

@RestController
public class MateriaController {

	@Autowired
	private MateriaService materiaServiceImp;

	@GetMapping("/materias")
	public ResponseEntity<List> getMaterias() {
		return ResponseEntity.ok().body(materiaServiceImp.getMateriasJson());
	}

	@PostMapping("/materias/modificarMateria")
	public ResponseEntity modificarMateria(@RequestBody MateriaSistemaJson materiaJson) throws FormatoNumeroIdException,
			MateriaNoExisteException, ExisteMateriaConElMismoCodigoException, CodigoInvalidoException,
			NombreInvalidoException, EstadoInvalidoException, DescripcionInvalidaException, HorarioInvalidoException {
		try {
			materiaServiceImp.actualizarMateria(materiaJson);
		} catch (FormatoNumeroIdException | MateriaNoExisteException | ExisteMateriaConElMismoCodigoException
				| CodigoInvalidoException | NombreInvalidoException | EstadoInvalidoException
				| DescripcionInvalidaException | HorarioInvalidoException e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ExceptionJson(e));
		}
		return ResponseEntity.ok().build();
	}

	@PutMapping("/materias/nuevaMateria")
	public ResponseEntity agregarNuevaMateria(@RequestBody MateriaSistemaJson materiaJson)
			throws DescripcionInvalidaException, CodigoInvalidoException, EstadoInvalidoException,
			ExisteMateriaConElMismoCodigoException, FormatoNumeroIdException, MateriaNoExisteException {
		try {
			materiaServiceImp.agregarNuevaMateria(materiaJson);
		} catch (DescripcionInvalidaException | CodigoInvalidoException | EstadoInvalidoException
				| ExisteMateriaConElMismoCodigoException | FormatoNumeroIdException | MateriaNoExisteException e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ExceptionJson(e));
		}
		return ResponseEntity.ok().build();
	}

	@GetMapping("/oferta-academica/materias/{idCarrera}")
	public ResponseEntity getMateriasParaCarrera(@PathVariable String idCarrera) throws FormatoNumeroIdException {
		return ResponseEntity.ok().body(materiaServiceImp.getMateriasParaCarrera(idCarrera));
	}

	@PostMapping("/materias/actualizar-preRequisitos/{idMateria}")
	public ResponseEntity actualizarPreRequisitos(@PathVariable String idMateria, @RequestBody List<IdJson> idsJson) {
		materiaServiceImp.actualizarPreRequisitos(idMateria, idsJson);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/materias/preRequisitos/{idMateria}")
	public ResponseEntity getPreRequisitosParaMateria(@PathVariable String idMateria) {
		return ResponseEntity.ok().body(materiaServiceImp.getPreRequisitosParaMateria(idMateria));
	}
}