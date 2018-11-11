package ar.edu.unq.inscripcionunq.spring.controller.miniobject;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unq.inscripcionunq.spring.model.Comision;
import ar.edu.unq.inscripcionunq.spring.model.Horario;

public class ComisionCompletaJson extends ComisionJson{
	
	public MateriaJson materia;
	public String nombreMateria;
	public List<HorarioJson> horarioJson = new ArrayList<>();
	public PeriodoJson periodo;

	public ComisionCompletaJson() {

	}

	public ComisionCompletaJson(Comision comision) {
		this.id = comision.getId();
		this.nombre = comision.getNombre();
		this.materia = new MateriaJson(comision.getMateria());
		this.nombreMateria = comision.getMateria().getNombre();
		List<Horario> horarios = comision.getHorarios();

		for (Horario horario : horarios) {
			horarioJson.add(new HorarioJson(horario));
		}
		this.cupo = comision.getCupo();
		this.periodo = new PeriodoJson(comision.getPeriodo());
	}
}