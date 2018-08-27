package ar.edu.unq.inscripcionunq.spring.controller.miniobject;

import java.time.LocalDateTime;

public class PollJson {

	public PollJson(Long id, String name, LocalDateTime startDate, LocalDateTime endDate) {
		this.id = id;
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public Long id;
	public String name;
	public LocalDateTime startDate;
	public LocalDateTime endDate;
}
