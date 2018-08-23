package ar.edu.unq.inscripcionunq.spring.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity(name = "Matter")
public class Matter {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String code;
	private String name;
	private Integer hours;
	@Enumerated(EnumType.STRING)
	private TypeStatus status = TypeStatus.ENABLED;
	@ManyToMany(cascade = CascadeType.ALL)
	private List<Career> careers = new ArrayList<Career>();

	public Matter(String code, String name, Integer hours, List<Career> listCareers) {
		setCode(code);
		setName(name);
		setHours(hours);
		this.careers = listCareers;
	}

	public Matter(String code, String name, Integer hours) {
		setCode(code);
		setName(name);
		setHours(hours);
	}

	public void addCareer(Career career) {
		careers.add(career);
	}

	public List<Career> getListCareers() {
		return careers;
	}

	public void removeCareer(Career career) {
		careers.remove(career);
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public Integer getHours() {
		return hours;
	}

	public TypeStatus getStatus() {
		return status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setHours(Integer hours) {
		this.hours = hours;
	}

	private void setStatus(TypeStatus status) {
		this.status = status;
	}

	public void disabled() {
		setStatus(TypeStatus.DISABLED);
	}
}
