package ar.edu.unq.inscripcionunq.spring.controller.miniobject;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unq.inscripcionunq.spring.model.Comision;
import ar.edu.unq.inscripcionunq.spring.model.Horario;

public class ComisionJson {
	
	public Long id;
	public String nombre;
	public List<HorarioJson> horarioJson = new ArrayList<>();
	public Integer cupo;

	public ComisionJson() {

	}

	public ComisionJson(Comision comision) {
		this.id = comision.getId();
		this.nombre = comision.getNombre();
		List<Horario> horarios = comision.getHorarios();

		for (Horario horario : horarios) {
			horarioJson.add(new HorarioJson(horario));
		}
		this.cupo = comision.getCupo();
	}
}