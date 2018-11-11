package ar.edu.unq.inscripcionunq.spring.controller.miniobject;

import ar.edu.unq.inscripcionunq.spring.model.Estudiante;

public class EstudianteJson {
	
	public String dni;
	public String nombre;
	public String apellido;
	public String email;
	public Long id;

	public EstudianteJson(Estudiante estudiante) {
		this.dni = estudiante.getDni();
		this.id = estudiante.getId();
		this.nombre = estudiante.getNombre();
		this.apellido = estudiante.getApellido();
		this.email = estudiante.getEmail();
	}
	
	public EstudianteJson(){
		
	}

}