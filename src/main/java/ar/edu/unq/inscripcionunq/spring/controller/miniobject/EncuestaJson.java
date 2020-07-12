package ar.edu.unq.inscripcionunq.spring.controller.miniobject;

import java.time.LocalDateTime;

public class EncuestaJson {
	
	public Long id;
	public String nombre;
	public LocalDateTime horaComienzo;
	public LocalDateTime horaFin;
	public boolean materiasRegistradas;
	public int limiteMaxMaterias; 

	public EncuestaJson(Long id, String nombre, LocalDateTime horaComienzo, LocalDateTime horaFin,
			boolean tieneMateriasRegistradas,int limiteMaxMaterias ) {
		this.id = id;
		this.nombre = nombre;
		this.horaComienzo = horaComienzo;
		this.horaFin = horaFin;
		this.materiasRegistradas = tieneMateriasRegistradas;
		this.limiteMaxMaterias = limiteMaxMaterias; 
	}
}