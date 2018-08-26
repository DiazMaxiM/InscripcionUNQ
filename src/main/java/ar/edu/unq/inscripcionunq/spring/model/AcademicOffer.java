package ar.edu.unq.inscripcionunq.spring.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity(name = "AcademicOffer")
public class AcademicOffer extends BaseEntity {

	private String name;
	private String description;
	@Enumerated(EnumType.STRING)
	private TypeStatus status = TypeStatus.ENABLED;
	@ManyToMany
	private List<Commission> commissions = new ArrayList<Commission>();
	@ManyToOne
	private Career career;

	public AcademicOffer(String name, String description, Career career) {
		this.name = name;
		this.description = description;
		this.career = career;
	}

	public AcademicOffer() {
	}

	public void addCommission(Commission commission) {
		this.commissions.add(commission);
	}

}
