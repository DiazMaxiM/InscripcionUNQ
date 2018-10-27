package ar.edu.unq.inscripcionunq.spring.model;

import javax.persistence.Column;
import javax.persistence.Entity;

import ar.edu.unq.inscripcionunq.spring.exception.EncryptionDecryptionAESException;
import ar.edu.unq.inscripcionunq.spring.exception.PasswordInvalidoException;

@Entity(name = "Usuario")
public class Usuario extends BaseEntity{

	private static final long serialVersionUID = 1L;
	@Column(unique = true)
	private String email; 
	private String password;
	
	public Usuario() {
		super();
	}
	
	public Usuario(String email, String password){
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

	public void validarPassword(String password) throws PasswordInvalidoException, EncryptionDecryptionAESException {
		if (!this.password.equals(password)) {
			throw new PasswordInvalidoException();
		};
	}
	
}
