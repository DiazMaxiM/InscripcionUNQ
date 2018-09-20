package ar.edu.unq.inscripcionunq.spring.model;

import javax.persistence.Entity;

@Entity(name = "Administrador")
public class Administrador extends BaseEntity{
	
	private String email; 
	private String password;
	
	public Administrador() {
		super();
	}
	
	public Administrador(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	

}
