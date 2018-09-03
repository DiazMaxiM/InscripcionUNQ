package ar.edu.unq.inscripcionunq.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unq.inscripcionunq.spring.controller.miniobject.StudentJson;
import ar.edu.unq.inscripcionunq.spring.controller.miniobject.SubjectJson;
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
	public ResponseEntity<Object> updateUserData(@RequestBody StudentJson studentJson) {
		Student studentRecived = new Student(studentJson.name, studentJson.lastName, studentJson.dni, studentJson.mail);
		Student student = StudentServiceImp.get(studentJson.id);
		if (student.isChange(studentRecived)) {
			// crear una incidencia
			student.update(studentRecived);
			StudentServiceImp.update(student);
		}
		return ResponseEntity.ok().body(null);
	}

	@GetMapping("/poll/userApprovedSubjects/{idUser}")
	public ResponseEntity<List<SubjectJson>> userApprovedSubjects(@PathVariable String idUser) {
		return ResponseEntity.ok().body(StudentServiceImp.userApprovedSubjects(idUser));
	}

	@PostMapping("/poll/userApprovedSubjects/{idUser}")
	public ResponseEntity<List<SubjectJson>> updateUserApprovedSubjects(@PathVariable String idUser,
			@RequestBody List<SubjectJson> subjectsJson) {
		StudentServiceImp.updateUserApprovedSubjects(idUser, subjectsJson);
		return ResponseEntity.ok().body(null);
	}

	@GetMapping("/poll/userDisapprovedSubjectsWithCommissionAvailable/{idUser}")
	public ResponseEntity<List<SubjectJson>> userDisapprovedSubjectsWithCommissionAvailable(
			@PathVariable String idUser) {
		List<SubjectJson> a = StudentServiceImp.userDisapprovedSubjectsWithCommissionAvailable(idUser);
		return ResponseEntity.ok().body(a);
	}
}