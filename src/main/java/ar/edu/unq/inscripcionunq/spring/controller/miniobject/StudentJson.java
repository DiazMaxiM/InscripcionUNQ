package ar.edu.unq.inscripcionunq.spring.controller.miniobject;

import ar.edu.unq.inscripcionunq.spring.model.Student;

public class StudentJson {

	public String dni;
	public String name;
	public String lastName;
	public String mail;
	public Long id;

	public StudentJson(Student student) {
		this.dni = student.getDni();
		this.id = student.getId();
		this.name = student.getName();
		this.lastName = student.getLastName();
		this.mail = student.getMail();
	}
	public StudentJson(){}

}
