package ar.edu.unq.inscripcionunq.spring.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
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
	@OneToMany(cascade = CascadeType.ALL)
	private List<Student> students = new ArrayList<Student>();
	@Enumerated(EnumType.STRING)
	private TypeStatus status = TypeStatus.ENABLED;

	public Poll(String name, LocalDateTime startDate, LocalDateTime endDate) {
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public void addAcademicOffer(AcademicOffer academicOffer) {
		this.academicsOffer.add(academicOffer);
	}

	public void disabled() {
		this.status = TypeStatus.DISABLED;
	}

	public long getId() {
		// TODO Auto-generated method stub
		return id;
	}

	public void addStudent(Student student) {
		students.add(student);
	}

}
