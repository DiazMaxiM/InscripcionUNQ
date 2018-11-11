package ar.edu.unq.inscripcionunq.spring.controller.miniobject;

import java.time.LocalDateTime;

public class EncuestaJson {
	
	public Long id;
	public String nombre;
	public LocalDateTime horaComienzo;
	public LocalDateTime horaFin;
	public Boolean materiasRegistradas;

	public EncuestaJson(Long id, String nombre, LocalDateTime horaComienzo, LocalDateTime horaFin,
			Boolean tieneMateriasRegistradas) {
		this.id = id;
		this.nombre = nombre;
		this.horaComienzo = horaComienzo;
		this.horaFin = horaFin;
		this.materiasRegistradas = tieneMateriasRegistradas;
	}
}