package ar.edu.unq.inscripcionunq.spring.controller.miniobject;

import ar.edu.unq.inscripcionunq.spring.model.Usuario;

public class UsuarioJson {
	
	public String email; 
	public  String password;
	
	public UsuarioJson(Usuario usuario) {
		this.email = usuario.getEmail();
	}

	public UsuarioJson() {
		super();
	}

}
