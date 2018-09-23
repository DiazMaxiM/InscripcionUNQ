package ar.edu.unq.inscripcionunq.spring.controller.miniobject;

import java.time.LocalTime;

import ar.edu.unq.inscripcionunq.spring.model.Horario;
import ar.edu.unq.inscripcionunq.spring.model.TypeDay;

public class HorarioJson {

	public TypeDay dia;
	public LocalTime horaComienzo;
	public LocalTime horaFin;

	public HorarioJson(Horario horario) {
		this.dia = horario.getDia();
		this.horaComienzo = horario.getHoraComienzo();
		this.horaFin = horaComienzo.plusMinutes((long) (horario.getCantidadDeHoras() * 60 - 1));
	}

	public HorarioJson() {

	}
}
