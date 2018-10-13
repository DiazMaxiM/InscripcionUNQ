package ar.edu.unq.inscripcionunq.spring.controller.miniobject;

import java.util.List;

public class EstudianteWebServiceJson {

	public String dni;
	public String nombre;
	public String apellido;
	public String email;
	public Boolean regularidad;
	public List<MateriaWebServiceJson> materiasAprobadas;
	public List<CarreraWebServiceJson> carreras;
}
