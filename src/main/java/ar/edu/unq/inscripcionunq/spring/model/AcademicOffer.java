package ar.edu.unq.inscripcionunq.spring.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity(name = "AcademicOffer")
public class AcademicOffer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
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

	public void addCommission(Commission commission) {
		this.commissions.add(commission);
	}

	public long getId() {
		// TODO Auto-generated method stub
		return id;
	}

}
