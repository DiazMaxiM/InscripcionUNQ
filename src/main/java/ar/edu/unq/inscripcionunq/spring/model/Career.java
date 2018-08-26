package ar.edu.unq.inscripcionunq.spring.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity(name = "Career")
public class Career extends BaseEntity {

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
