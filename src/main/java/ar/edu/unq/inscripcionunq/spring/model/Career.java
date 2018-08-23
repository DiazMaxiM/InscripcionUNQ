package ar.edu.unq.inscripcionunq.spring.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "Career")
public class Career {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String code;
	private String description;
	@Enumerated(EnumType.STRING)
	private TypeStatus status = TypeStatus.ENABLED;

	public Career() {

	}

	public Career(String code, String description) {
		setCode(code);
		setDescription(description);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TypeStatus getStatus() {
		return this.status;
	}

	private void setStatus(TypeStatus status) {
		this.status = status;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void disabled() {
		setStatus(TypeStatus.DISABLED);
	}

}
