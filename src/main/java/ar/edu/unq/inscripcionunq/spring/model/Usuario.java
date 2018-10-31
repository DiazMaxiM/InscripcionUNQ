package ar.edu.unq.inscripcionunq.spring.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import ar.edu.unq.inscripcionunq.spring.exception.EncryptionDecryptionAESException;
import ar.edu.unq.inscripcionunq.spring.exception.PasswordInvalidoException;

@Entity(name = "Usuario")
public class Usuario extends BaseEntity{

	private static final long serialVersionUID = 1L;
	@Column(unique = true)
	private String email; 
	private String password;
	private String dni; 
    @ElementCollection(targetClass=TipoPerfil.class)
    @Enumerated(EnumType.STRING) 
	private List<TipoPerfil> perfiles = new ArrayList<>();
	
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
	
	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void validarPassword(String password) throws PasswordInvalidoException, EncryptionDecryptionAESException {
		if (!this.password.equals(password)) {
			throw new PasswordInvalidoException();
		};
	}
	
	public void agregarPerfil(TipoPerfil perfil) {
		this.perfiles.add(perfil);
	}

	public List<TipoPerfil> getPerfiles() {
		return perfiles;
	}

	public void setPerfiles(List<TipoPerfil> perfiles) {
		this.perfiles = perfiles;
	}
	
}
