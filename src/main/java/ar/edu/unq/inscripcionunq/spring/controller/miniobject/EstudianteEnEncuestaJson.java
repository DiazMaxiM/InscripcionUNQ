package ar.edu.unq.inscripcionunq.spring.controller.miniobject;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unq.inscripcionunq.spring.model.Carrera;
import ar.edu.unq.inscripcionunq.spring.model.Estudiante;

public class EstudianteEnEncuestaJson {
	
	public String dni;
	public String nombre;
	public String apellido;
	public String email;
	public Long id;
	public List<CarreraJson> carreras; 

	public EstudianteEnEncuestaJson(Estudiante estudiante) {
		this.dni = estudiante.getDni();
		this.id = estudiante.getId();
		this.nombre = estudiante.getNombre();
		this.apellido = estudiante.getApellido();
		this.email = estudiante.getEmail();
		this.carreras = this.getCarrerasJson(estudiante.getCarrerasInscripto());
	}

	private List<CarreraJson> getCarrerasJson(List<Carrera> carrerasInscripto) {
		List<CarreraJson> carrerasJson = new ArrayList<CarreraJson>();
		for(Carrera carrera : carrerasInscripto) {
		  carrerasJson.add(new CarreraJson(carrera));
		}
		return carrerasJson;
	}
	
}
