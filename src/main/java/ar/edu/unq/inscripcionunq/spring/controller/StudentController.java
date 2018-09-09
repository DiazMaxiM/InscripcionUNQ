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
import ar.edu.unq.inscripcionunq.spring.controller.miniobject.StudentJson;
import ar.edu.unq.inscripcionunq.spring.controller.miniobject.SubjectJson;
import ar.edu.unq.inscripcionunq.spring.exception.ApellidoInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.EmailInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.IdNumberFormatException;
import ar.edu.unq.inscripcionunq.spring.exception.NombreInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.ObjectNotFoundinDBException;
import ar.edu.unq.inscripcionunq.spring.exception.StudentNotExistenException;
import ar.edu.unq.inscripcionunq.spring.model.Student;
import ar.edu.unq.inscripcionunq.spring.service.StudentService;
import ar.edu.unq.inscripcionunq.spring.service.SubjectService;

@RestController
public class StudentController {
	@Autowired
	private StudentService StudentServiceImp;

	@Autowired
	private SubjectService subjectServiceImp;

	@PostMapping("/poll/userData")
	public ResponseEntity updateUserData(@RequestBody StudentJson studentJson) throws NombreInvalidoException, ApellidoInvalidoException, EmailInvalidoException {
		Student studentRecived = new Student(studentJson.name, studentJson.lastName, studentJson.dni, studentJson.mail);
		Student student;
		try {
			student = StudentServiceImp.get(studentJson.id);
		} catch (ObjectNotFoundinDBException e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
					.body(new ExceptionJson(new StudentNotExistenException()));
		}
		if (student.isChange(studentRecived)) {
			try {
				// crear una incidencia
				student.update(studentRecived);
				StudentServiceImp.update(student);
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

	@GetMapping("/poll/userApprovedSubjects/{idUser}")
	public ResponseEntity userApprovedSubjects(@PathVariable String idUser) {
		try {
			return ResponseEntity.ok().body(StudentServiceImp.userApprovedSubjects(idUser));
		} catch (IdNumberFormatException | StudentNotExistenException e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ExceptionJson(e));
		}
	}

	@PostMapping("/poll/userApprovedSubjects/{idUser}")
	public ResponseEntity updateUserApprovedSubjects(@PathVariable String idUser,
			@RequestBody List<SubjectJson> subjectsJson) {
		try {
			StudentServiceImp.updateUserApprovedSubjects(idUser, subjectsJson);
		} catch (IdNumberFormatException | StudentNotExistenException e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ExceptionJson(e));
		}
		return ResponseEntity.ok().body(null);
	}

	@GetMapping("/poll/userDisapprovedSubjectsWithCommissionAvailable/{idUser}")
	public ResponseEntity userDisapprovedSubjectsWithCommissionAvailable(@PathVariable String idUser) {
		List<SubjectJson> a;
		try {
			return ResponseEntity.ok().body(StudentServiceImp.userDisapprovedSubjectsWithCommissionAvailable(idUser));
		} catch (IdNumberFormatException | StudentNotExistenException e) {
			// TODO Auto-generated catch block
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ExceptionJson(e));
		}
	}
}