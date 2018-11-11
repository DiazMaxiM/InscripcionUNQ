package ar.edu.unq.inscripcionunq.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unq.inscripcionunq.spring.controller.miniobject.ExceptionJson;
import ar.edu.unq.inscripcionunq.spring.controller.miniobject.EstudianteJson;
import ar.edu.unq.inscripcionunq.spring.controller.miniobject.MateriaJson;
import ar.edu.unq.inscripcionunq.spring.exception.ApellidoInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.EmailInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.FormatoNumeroIdException;
import ar.edu.unq.inscripcionunq.spring.exception.NombreInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.ObjectoNoEncontradoEnBDException;
import ar.edu.unq.inscripcionunq.spring.exception.EstudianteNoExisteException;
import ar.edu.unq.inscripcionunq.spring.model.Estudiante;
import ar.edu.unq.inscripcionunq.spring.service.EstudianteService;

@RestController
public class EstudianteController {
	
	@Autowired
	private EstudianteService estudianteServiceImp;

	@PostMapping("/poll/userData")
	public ResponseEntity updateUserData(@RequestBody EstudianteJson estudianteJson) throws NombreInvalidoException, ApellidoInvalidoException, EmailInvalidoException {
		Estudiante estudianteRecibido = new Estudiante(estudianteJson.nombre, estudianteJson.apellido, estudianteJson.dni, estudianteJson.email);
		Estudiante estudiante;
		try {
			estudiante = estudianteServiceImp.get(estudianteJson.id);
		} catch (ObjectoNoEncontradoEnBDException e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
					.body(new ExceptionJson(new EstudianteNoExisteException()));
		}
		if (estudiante.estudianteCambio(estudianteRecibido)) {
			try {
				estudiante.actualizarEstudiante(estudianteRecibido);
				estudianteServiceImp.update(estudiante);
			} catch (NombreInvalidoException n) {
				return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
						.body(new ExceptionJson(new NombreInvalidoException()));
			} catch (ApellidoInvalidoException a) {
				return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
						.body(new ExceptionJson(new ApellidoInvalidoException()));
			} catch (EmailInvalidoException e) {
				return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
						.body(new ExceptionJson(new EmailInvalidoException()));
			}
		}
		return ResponseEntity.ok().body(null);
	}

	@GetMapping("/poll/userApprovedSubjects/{idUsuario}")
	public ResponseEntity userApprovedSubjects(@PathVariable String idUsuario) {
		try {
			return ResponseEntity.ok().body(estudianteServiceImp.materiasAprobadasDeUsuario(idUsuario));
		} catch (FormatoNumeroIdException | EstudianteNoExisteException e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ExceptionJson(e));
		}
	}

	@PostMapping("/poll/userApprovedSubjects/{idUsuario}")
	public ResponseEntity updateUserApprovedSubjects(@PathVariable String idUsuario,
			@RequestBody List<MateriaJson> materiasJson) {
		try {
			estudianteServiceImp.actualizarMateriasAprobadasDeUsuario(idUsuario, materiasJson);
		} catch (FormatoNumeroIdException | EstudianteNoExisteException e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ExceptionJson(e));
		}
		return ResponseEntity.ok().body(null);
	}

	@GetMapping("/poll/userDisapprovedSubjectsWithCommissionAvailable/{idUsuario}")
	public ResponseEntity userDisapprovedSubjectsWithCommissionAvailable(@PathVariable String idUsuario) {
		List<MateriaJson> a;
		try {
			return ResponseEntity.ok().body(estudianteServiceImp.materiasDesaprobadasConComisionesDisponiblesDeUsuario(idUsuario));
		} catch (FormatoNumeroIdException | EstudianteNoExisteException e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ExceptionJson(e));
		}
	}
}