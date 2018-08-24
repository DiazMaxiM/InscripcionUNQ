package ar.edu.unq.inscripcionunq.spring.model;

import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.TreeMap;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity(name = "Commission")
public class Commission {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private EnumMap<Day, TreeMap<LocalDateTime, Integer>> hours;
	private Integer quota;
	@OneToOne
	private Matter matter;
	@Enumerated(EnumType.STRING)
	private TypeStatus status = TypeStatus.ENABLED;

}
