package inscripcionunq;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import ar.edu.unq.inscripcionunq.spring.exception.ApellidoInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.EmailInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.NombreInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.VariasComisionesDeUnaMateriaException;
import ar.edu.unq.inscripcionunq.spring.model.Carrera;
import ar.edu.unq.inscripcionunq.spring.model.Comision;
import ar.edu.unq.inscripcionunq.spring.model.Encuesta;
import ar.edu.unq.inscripcionunq.spring.model.Estudiante;
import ar.edu.unq.inscripcionunq.spring.model.Materia;

public class EstudianteTest {
	
	private Estudiante estudiante;

	@Before
	public void setUp() throws Exception {
		estudiante = new Estudiante("Ana", "Martínez", "40111999", "ana.m@gmail.com");
	}

	@Test
	public void testCrearEstudianteyVerificarNombreApellidoyEmail() {
		assertEquals("Ana", estudiante.getNombre());
		assertEquals("Martínez", estudiante.getApellido());
		assertEquals("40111999", estudiante.getDni());
		assertEquals("ana.m@gmail.com", estudiante.getEmail());
	}

	@Test
	public void testCrearEstudianteyVerificarRegularidad() {
		assertTrue(estudiante.getRegularidad());
	}

	@Test
	public void testAgregarMateriaAprobadayVerificarSiEstaAprobadaYSiFueAgregada() {
		Materia materia = Mockito.mock(Materia.class);
		estudiante.agregarMateriaAprobada(materia);

		assertTrue(estudiante.estaAprobada(materia));
		assertTrue(estudiante.getMateriasAprobadas().contains(materia));
	}

	@Test
	public void testAgregarCarrerayVerificarSiFueAgregadaACarreras() {
		Carrera carrera = Mockito.mock(Carrera.class);
		estudiante.agregarInscripcionACarrera(carrera);

		assertTrue(estudiante.getCarrerasInscripto().contains(carrera));
	}

	@Test(expected = NombreInvalidoException.class)
	public void testActualizarEstudianteConNombreVacioLanzaExcepcion()
			throws NombreInvalidoException, ApellidoInvalidoException, EmailInvalidoException {
		Estudiante estudianteConCambios = new Estudiante("", "Martínez", "40111999", "ana.mh@gmail.com");
		estudiante.actualizarEstudiante(estudianteConCambios);
	}

	@Test(expected = ApellidoInvalidoException.class)
	public void testActualizarEstudianteConApellidoVacioLanzaExcepcion()
			throws NombreInvalidoException, ApellidoInvalidoException, EmailInvalidoException {
		Estudiante estudianteConCambios = new Estudiante("Ana", "", "40111999", "ana.mh@gmail.com");
		estudiante.actualizarEstudiante(estudianteConCambios);
	}

	@Test(expected = EmailInvalidoException.class)
	public void testActualizarEstudianteConEmailInvalidoLanzaExcepcion()
			throws NombreInvalidoException, ApellidoInvalidoException, EmailInvalidoException {
		Estudiante estudianteConCambios = new Estudiante("Ana", "Martínez", "40111999", "ana.mh@gmail");
		estudiante.actualizarEstudiante(estudianteConCambios);
	}

	@Test
	public void testAgregarIntencionDeInscripcionAComisionyVerificarSiFueAgregada()
			throws VariasComisionesDeUnaMateriaException {
		Comision comision = Mockito.mock(Comision.class);
		estudiante.agregarRegistroComisiones(comision);

		assertTrue(estudiante.getRegistroComisiones().contains(comision));
	}

	@Test
	public void testAgregarEncuestaYVerificarSiFueAgregada() {
		Encuesta encuesta = Mockito.mock(Encuesta.class);
		estudiante.setEncuesta(encuesta);

		assertEquals(encuesta, estudiante.getEncuesta());
	}
}