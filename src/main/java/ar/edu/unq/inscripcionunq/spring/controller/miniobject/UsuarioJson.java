package ar.edu.unq.inscripcionunq.spring.controller.miniobject;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unq.inscripcionunq.spring.model.TipoPerfil;
import ar.edu.unq.inscripcionunq.spring.model.Usuario;

public class UsuarioJson {
	
	public String email; 
	public String password;
	public String nombre; 
	public String apellido; 
	public List<String> perfiles = new ArrayList<>();
	public Long id;
	public String dni;
	
	public UsuarioJson(Usuario usuario) {
		this.nombre = usuario.getNombre();
		this.apellido = usuario.getApellido();
		this.email = usuario.getEmail();
		this.id = usuario.getId();
		this.dni = usuario.getDni();
		for(TipoPerfil perfil : usuario.getPerfiles()) {
			perfiles.add(perfil.name());
		}
	}

	public UsuarioJson() {
		super();
	}
}