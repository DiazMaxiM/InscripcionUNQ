package inscripcionunq;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import ar.edu.unq.inscripcionunq.spring.exception.ApellidoInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.EmailInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.NombreInvalidoException;
import ar.edu.unq.inscripcionunq.spring.model.Career;
import ar.edu.unq.inscripcionunq.spring.model.Commission;
import ar.edu.unq.inscripcionunq.spring.model.Poll;
import ar.edu.unq.inscripcionunq.spring.model.Student;
import ar.edu.unq.inscripcionunq.spring.model.Subject;

public class StudentTest {
	private Student estudiante;

	@Before
	public void setUp() throws Exception {
		estudiante = new Student("Ana", "Martínez", "40111999", "ana.m@gmail.com");
	}

	@Test
	public void testCrearEstudianteyVerificarNombreApellidoyEmail() {
		assertEquals("Ana", estudiante.getName());
		assertEquals("Martínez", estudiante.getLastName());
		assertEquals("40111999", estudiante.getDni());
		assertEquals("ana.m@gmail.com", estudiante.getMail());
	}
	
	@Test
	public void testCrearEstudianteyVerificarRegularidad() {
		assertTrue(estudiante.getRegularity());
	}
	
	@Test
	public void testAgregarMateriaAprobadayVerificarSiEstaAprobadaYSiFueAgregada() {
		Subject materia = Mockito.mock(Subject.class);
		estudiante.addMatterAprroved(materia);
		
		assertTrue(estudiante.isApproved(materia));
		assertTrue(estudiante.getSubjectsApproved().contains(materia));
	}
	
	@Test
	public void testAgregarCarrerayVerificarSiFueAgregadaACarreras() {
		Career carrera = Mockito.mock(Career.class);
		estudiante.addcareerInscription(carrera);
		
		assertTrue(estudiante.getCareersInscription().contains(carrera));
	}

	@Test(expected=NombreInvalidoException.class)
	public void testActualizarEstudianteConNombreVacioLanzaExcepcion () throws NombreInvalidoException, ApellidoInvalidoException, EmailInvalidoException {
		Student estudianteConCambios = new Student("", "Martínez", "40111999", "ana.mh@gmail.com");
		estudiante.update(estudianteConCambios);
	}
	
	@Test(expected=ApellidoInvalidoException.class)
	public void testActualizarEstudianteConApellidoVacioLanzaExcepcion () throws NombreInvalidoException, ApellidoInvalidoException, EmailInvalidoException {
		Student estudianteConCambios = new Student("Ana", "", "40111999", "ana.mh@gmail.com");
		estudiante.update(estudianteConCambios);
	}
	
	@Test(expected=EmailInvalidoException.class)
	public void testActualizarEstudianteConEmailInvalidoLanzaExcepcion () throws NombreInvalidoException, ApellidoInvalidoException, EmailInvalidoException {
		Student estudianteConCambios = new Student("Ana", "Martínez", "40111999", "ana.mh@gmail");
		estudiante.update(estudianteConCambios);
	}
	
	@Test
	public void testAgregarIntencionDeInscripcionAComisionyVerificarSiFueAgregada() {
		Commission comision = Mockito.mock(Commission.class);
		estudiante.addCommissionRegistration(comision);
		
		assertTrue(estudiante.getCommissionsRegistration().contains(comision));
	}
	
	@Test
	public void testAgregarEncuestaYVerificarSiFueAgregada() {
		Poll encuesta = Mockito.mock(Poll.class);
		estudiante.setPoll(encuesta);
		
		assertEquals(encuesta, estudiante.getPoll());
	}
}