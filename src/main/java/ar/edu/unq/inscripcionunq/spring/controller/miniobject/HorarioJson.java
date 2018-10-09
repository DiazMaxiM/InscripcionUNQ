package ar.edu.unq.inscripcionunq.spring.controller.miniobject;

import java.time.LocalTime;

import ar.edu.unq.inscripcionunq.spring.model.Horario;
import ar.edu.unq.inscripcionunq.spring.model.TypeDay;

public class HorarioJson {

	public TypeDay dia;
	public Hora horaComienzo;
	public Hora horaFin;
	public Float duracion;

	public HorarioJson(Horario horario) {
		this.dia = horario.getDia();
		this.horaComienzo = this.crearHorario(horario.getHoraComienzo());
		this.horaFin = this.crearHorario(horario.getHoraComienzo().plusMinutes((long) (horario.getCantidadDeHoras() * 60 - 1)));
	    this.duracion = horario.getCantidadDeHoras();
	}

	private Hora crearHorario(LocalTime horario) {
		
		return new Hora(horario.getHour(),horario.getMinute());
	}

	public HorarioJson() {

	}
}
