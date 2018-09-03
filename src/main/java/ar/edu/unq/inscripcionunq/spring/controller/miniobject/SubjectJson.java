package ar.edu.unq.inscripcionunq.spring.controller.miniobject;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unq.inscripcionunq.spring.model.Subject;

public class SubjectJson {

	public Long id;
	public String code;
	public String name;
	public Boolean approved;
	public List<CommissionJson> commissionsJson = new ArrayList<CommissionJson>();

	public SubjectJson() {

	}

	public SubjectJson(Subject subject, Boolean bool) {
		this.code = subject.getCode();
		this.name = subject.getName();
		this.approved = bool;
		this.id = subject.getId();
	}

	public SubjectJson addCommissionJson(List<CommissionJson> commissionJson) {
		commissionsJson.addAll(commissionJson);
		return this;
	}
}
