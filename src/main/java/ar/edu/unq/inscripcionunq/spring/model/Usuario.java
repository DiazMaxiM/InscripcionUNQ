package ar.edu.unq.inscripcionunq.spring.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import ar.edu.unq.inscripcionunq.spring.exception.EncriptarDesencriptarAESException;
import ar.edu.unq.inscripcionunq.spring.exception.PasswordInvalidoException;

@Entity(name = "Usuario")
public class Usuario extends BaseEntity{

	private static final long serialVersionUID = 1L;
	private String nombre;
	private String apellido;
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
	
	public Usuario(String nombre, String apellido, String email, String dni){
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
		this.dni = dni;
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

	public void validarPassword(String password) throws PasswordInvalidoException, EncriptarDesencriptarAESException {
		if (!this.password.equals(password)) {
			throw new PasswordInvalidoException();
		}
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
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public void actualizarDatos(Usuario usuarioActualizado) {
		this.setNombre(usuarioActualizado.getNombre());
		this.setApellido(usuarioActualizado.getApellido());
		this.setEmail(usuarioActualizado.getEmail());
		this.setDni(usuarioActualizado.getDni());
	}	
}