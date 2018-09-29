package ar.edu.unq.inscripcionunq.spring.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import ar.edu.unq.inscripcionunq.spring.exception.ApellidoInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.EmailInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.NombreInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.VariasComisionesDeUnaMateriaException;
import ar.edu.unq.inscripcionunq.spring.validacion.Validacion;

@Entity(name = "Estudiante")
public class Estudiante extends BaseEntity {
	private String dni;
	private String nombre;
	private String apellido;
	private String email;
	private Boolean regularidad = true;
	@Enumerated(EnumType.STRING)
	private TypeStatus estado = TypeStatus.ENABLED;
	@ManyToMany
	@LazyCollection(LazyCollectionOption.TRUE)
	private List<Carrera> carrerasInscripto = new ArrayList<Carrera>();
	@ManyToMany
	@LazyCollection(LazyCollectionOption.TRUE)
	private List<Materia> materiasAprobadas = new ArrayList<Materia>();
	@ManyToMany
	@LazyCollection(LazyCollectionOption.TRUE)
	private List<Comision> registroComisiones = new ArrayList<Comision>();
	@ManyToOne
	@LazyCollection(LazyCollectionOption.TRUE)
	private Encuesta encuesta;

	public Estudiante() {

	}

	public Estudiante(String nombre, String apellido, String dni, String email) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.dni = dni;
		this.email = email;
	}

	public void agregarMateriaAprobada(Materia materia) {
		materiasAprobadas.add(materia);
	}

	public void agregarInscripcionACarrera(Carrera carrera) {
		carrerasInscripto.add(carrera);
	}

	public void agregarRegistroComisiones(Comision comision) throws VariasComisionesDeUnaMateriaException {
		Materia materia = comision.getMateria();
		List<Comision> comisiones = registroComisiones.stream()
				.filter(c -> c.getMateria().getId().equals(materia.getId())).collect(Collectors.toList());
		if (comisiones.isEmpty()) {
			registroComisiones.add(comision);
		} else {
			throw new VariasComisionesDeUnaMateriaException();
		}
	}

	public String getDni() {
		return dni;
	}

	public String getNombre() {
		return nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public String getEmail() {
		return email;
	}

	public boolean estudianteCambio(Estudiante estudiante) {
		return estudiante.dni != this.dni || estudiante.nombre != this.nombre 
				|| estudiante.apellido != this.apellido
				|| estudiante.email != this.email;
	}

	public void update(Estudiante estudiante)
			throws NombreInvalidoException, ApellidoInvalidoException, EmailInvalidoException {
            Validacion.validarEstudiante(estudiante);
			this.dni = estudiante.dni;
			this.email = estudiante.email;
			this.apellido = estudiante.apellido;
			this.nombre = estudiante.nombre;
	}

	public List<Carrera> getCarrerasInscripto() {
		return carrerasInscripto;
	}

	public Boolean estaAprobada(Materia materia) {
		return materiasAprobadas.contains(materia);
	}

	public List<Materia> getMateriasAprobadas() {
		return materiasAprobadas;
	}

	public void setMateriasAprobadas(List<Materia> materiasAprobadas) {
		this.materiasAprobadas = materiasAprobadas;
	}

	public void setEncuesta(Encuesta encuesta) {
		this.encuesta = encuesta;
	}

	public Encuesta getEncuesta() {
		return encuesta;
	}

	public boolean getRegularidad() {
		return regularidad;
	}

	public List<Comision> getRegistroComisiones() {
		return registroComisiones;
	}

	public void eliminarTodasLasComisionesInscripto() {
		this.registroComisiones = new ArrayList<Comision>();
	}
}
