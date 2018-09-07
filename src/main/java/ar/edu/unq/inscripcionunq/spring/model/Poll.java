package ar.edu.unq.inscripcionunq.spring.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity(name = "Poll")

public class Poll extends BaseEntity {

	private String name;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	@ManyToMany(fetch = FetchType.LAZY)
	private List<AcademicOffer> academicsOffer = new ArrayList<AcademicOffer>();
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Student> students = new ArrayList<Student>();
	@Enumerated(EnumType.STRING)
	private TypeStatus status = TypeStatus.ENABLED;

	public Poll() {
	}

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

	public void addStudent(Student student) {
		students.add(student);
		student.setPoll(this);
	}

	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	public LocalDateTime getStartDate() {
		// TODO Auto-generated method stub
		return startDate;
	}

	public LocalDateTime endDate() {
		// TODO Auto-generated method stub
		return endDate;
	}
	
	public TypeStatus getStatus() {
		return status;
	}
	
	public List<AcademicOffer> getAcademicOffers() {
		return academicsOffer;
	}
	
	public List<Student> getStudents(){
		return students;
	}

}
