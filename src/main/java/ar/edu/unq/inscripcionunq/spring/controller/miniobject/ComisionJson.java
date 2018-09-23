package ar.edu.unq.inscripcionunq.spring.controller.miniobject;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unq.inscripcionunq.spring.model.Comision;
import ar.edu.unq.inscripcionunq.spring.model.Horario;


public class ComisionJson {
	public Long id;
	public String nombre;
	public List<HorarioJson> horarioJson = new ArrayList<HorarioJson>();

	public ComisionJson() {

	}

	public ComisionJson(Comision comision) {
		this.id = comision.getId();
		this.nombre = comision.getNombre();
		List<Horario> intervals = comision.getHorarios();

		for (Horario interval : intervals) {
			horarioJson.add(new HorarioJson(interval));
		}

	}

}
