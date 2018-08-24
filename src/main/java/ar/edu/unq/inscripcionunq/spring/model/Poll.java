package ar.edu.unq.inscripcionunq.spring.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity(name = "Poll")

public class Poll {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	@ManyToMany
	private List<AcademicOffer> academicsOffer = new ArrayList<AcademicOffer>();
	@OneToMany
	private List<Student> students = new ArrayList<Student>();
	@Enumerated(EnumType.STRING)
	private TypeStatus status = TypeStatus.ENABLED;

}
