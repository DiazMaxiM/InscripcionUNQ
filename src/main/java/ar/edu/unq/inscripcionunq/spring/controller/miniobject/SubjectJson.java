package ar.edu.unq.inscripcionunq.spring.controller.miniobject;

import ar.edu.unq.inscripcionunq.spring.model.Subject;

public class SubjectJson {

	public Long id;
	public String code;
	public String name;
	public Boolean approved;

	public SubjectJson() {

	}

	public SubjectJson(Subject subject, Boolean bool) {
		this.code = subject.getCode();
		this.name = subject.getName();
		this.approved = bool;
		this.id = subject.getId();
	}
}
