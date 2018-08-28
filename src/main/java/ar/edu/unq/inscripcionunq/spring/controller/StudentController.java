package ar.edu.unq.inscripcionunq.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unq.inscripcionunq.spring.controller.miniobject.StudentJson;
import ar.edu.unq.inscripcionunq.spring.model.Student;
import ar.edu.unq.inscripcionunq.spring.service.StudentService;

@RestController
public class StudentController {
	@Autowired
	private StudentService StudentServiceImp;

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
}