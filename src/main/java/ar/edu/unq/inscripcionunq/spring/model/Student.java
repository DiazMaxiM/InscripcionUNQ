package ar.edu.unq.inscripcionunq.spring.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity(name = "Student")
public class Student extends BaseEntity {

	private String dni;
	private String name;
	private String lastName;
	private String mail;
	private Boolean regularity = true;
	@Enumerated(EnumType.STRING)
	private TypeStatus status = TypeStatus.ENABLED;
	@ManyToMany
	@LazyCollection(LazyCollectionOption.TRUE)
	private List<Career> careersInscription = new ArrayList<Career>();
	@ManyToMany
	@LazyCollection(LazyCollectionOption.TRUE)
	private List<Subject> subjectsApproved = new ArrayList<Subject>();
	@ManyToMany
	@LazyCollection(LazyCollectionOption.TRUE)
	private List<Commission> commissionsRegistration = new ArrayList<Commission>();
	@ManyToOne
	@LazyCollection(LazyCollectionOption.TRUE)
	private Poll poll;

	public Student() {

	}

	public Student(String name, String lastName, String dni, String mail) {
		this.name = name;
		this.lastName = lastName;
		this.dni = dni;
		this.mail = mail;
	}

	public void addMatterAprroved(Subject matter) {
		subjectsApproved.add(matter);
	}

	public void addcareerInscription(Career career) {
		careersInscription.add(career);
	}

	public void addCommissionRegistration(Commission commission) {
		commissionsRegistration.add(commission);
	}

	public String getDni() {
		return dni;
	}

	public String getName() {
		return name;
	}

	public String getLastName() {
		return lastName;
	}

	public String getMail() {
		return mail;
	}

	public boolean isChange(Student student) {
		// TODO Auto-generated method stub
		return student.dni != this.dni || student.name != this.name || student.lastName != this.lastName
				|| student.mail != this.mail;
	}

	public void update(Student student) {
		this.dni = student.dni;
		this.mail = student.mail;
		this.lastName = student.lastName;
		this.name = student.name;
	}

	public List<Career> getCareersInscription() {
		return careersInscription;
	}

	public Boolean isApproved(Subject subject) {
		return subjectsApproved.contains(subject);
	}

	public List<Subject> getSubjectsApproved() {
		return subjectsApproved;
	}

	public void setSubjectsApproved(List<Subject> newSubjectsApproved) {
		this.subjectsApproved = newSubjectsApproved;
	}

	public void setPoll(Poll poll) {
		this.poll = poll;
	}

	public Poll getPoll() {
		return poll;
	}
}
