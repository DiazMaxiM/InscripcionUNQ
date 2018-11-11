package inscripcionunq;

import static org.junit.Assert.*;
import java.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ar.edu.unq.inscripcionunq.spring.model.OfertaAcademica;
import ar.edu.unq.inscripcionunq.spring.model.Periodo;
import ar.edu.unq.inscripcionunq.spring.model.Encuesta;
import ar.edu.unq.inscripcionunq.spring.model.Estudiante;
import ar.edu.unq.inscripcionunq.spring.model.TipoEstado;

public class EncuestaTest {
	
	private Encuesta encuesta;
	private LocalDateTime inicio;
	private LocalDateTime fin;

	@Before
	public void setUp() throws Exception {
		inicio = inicio.of(2018, 8, 25, 00, 00);
		fin = fin.of(2018, 12, 1, 00, 00);
		Periodo periodo = Mockito.mock(Periodo.class);
		encuesta = new Encuesta("Encuesta segundo semestre 2018", inicio, fin, periodo);
	}

	@Test
	public void crearUnaEncuestaYVerificarNombreFechaDeInicioFechaDeFinYEstado() {
		assertEquals("Encuesta segundo semestre 2018", encuesta.getNombre());
		assertEquals(inicio, encuesta.getHoraComienzo());;
		assertEquals(fin, encuesta.getHoraFin());
		assertEquals(TipoEstado.ENABLED, encuesta.getEstado());
	}
	
	@Test
	public void deshabilitarEncuestaYVerificarNuevoEstado() {
		encuesta.deshabilitar();
		
		assertEquals(TipoEstado.DISABLED, encuesta.getEstado());
	}
	
	@Test
	public void agregarOfertaAcademicaYVerificarSiFueAgregadaEnLasOfertasAcademicasDeLaEncuesta() {
		OfertaAcademica academicOffer = Mockito.mock(OfertaAcademica.class);
		encuesta.agregarOfertaAcademica(academicOffer);
		
		assertTrue(encuesta.getOfertasAcademicas().contains(academicOffer));
	}
	
	@Test
	public void agregarEstudianteEnEncuestaYVerificarSiFueAgregadoEnEstudiantes() {
		Estudiante student = Mockito.mock(Estudiante.class);
		encuesta.agregarEstudiante(student);
		
		assertTrue(encuesta.getEstudiantes().contains(student));
	}
}