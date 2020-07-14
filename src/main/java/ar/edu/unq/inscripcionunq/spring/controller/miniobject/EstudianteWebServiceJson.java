package ar.edu.unq.inscripcionunq.spring.controller.miniobject;

import java.util.List;

public class EstudianteWebServiceJson {

	public DatosPersonalesEstudianteWebServiceJson datos_personales;
	public String legajo;
	public Boolean es_regular;
	public List<MateriaWebServiceJson> cursadas;
	public List<CarreraWebServiceJson> carreras;
	public String promedio;
	public String id;
}
