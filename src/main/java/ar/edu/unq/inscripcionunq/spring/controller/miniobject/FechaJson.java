package ar.edu.unq.inscripcionunq.spring.controller.miniobject;

import java.time.LocalDateTime;

public class FechaJson {
	
	public int dia;
	public int mes;
	public int anho;	
	public Hora horario;

	public FechaJson(LocalDateTime horaComienzo) {
		this.dia = horaComienzo.getDayOfMonth();
		this.mes = horaComienzo.getMonthValue();
		this.anho = horaComienzo.getYear();
		this.horario = new Hora(horaComienzo.getHour(),horaComienzo.getMinute());
		
	}
	
	public FechaJson() {
		
	}
}
