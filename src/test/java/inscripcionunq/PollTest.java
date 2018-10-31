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
import ar.edu.unq.inscripcionunq.spring.model.TypeStatus;

public class PollTest {
	private Encuesta poll;
	private LocalDateTime start;
	private LocalDateTime end;

	@Before
	public void setUp() throws Exception {
		start = start.of(2018, 8, 25, 00, 00);
		end = end.of(2018, 12, 1, 00, 00);
		Periodo periodo = Mockito.mock(Periodo.class);
		poll = new Encuesta("Encuesta segundo semestre 2018", start, end, periodo);
	}

	@Test
	public void createAPollAndVerifyNameStartDateEndDateAndStatus() {
		assertEquals("Encuesta segundo semestre 2018", poll.getNombre());
		assertEquals(start, poll.getHoraComienzo());;
		assertEquals(end, poll.getHoraFin());
		assertEquals(TypeStatus.ENABLED, poll.getEstado());
	}
	
	@Test
	public void disabledPollAndVerifyNewStatus() {
		poll.deshabilitar();
		
		assertEquals(TypeStatus.DISABLED, poll.getEstado());
	}
	
	@Test
	public void addAcademicOfferAndVerifyIfItWasAddedInAcademicOffers() {
		OfertaAcademica academicOffer = Mockito.mock(OfertaAcademica.class);
		poll.agregarOfertaAcademica(academicOffer);
		
		assertTrue(poll.getOfertasAcademicas().contains(academicOffer));
	}
	
	@Test
	public void addStudentInPollAndVerifyIfItWasAddedInStudents() {
		Estudiante student = Mockito.mock(Estudiante.class);
		poll.agregarEstudiante(student);
		
		assertTrue(poll.getEstudiantes().contains(student));
	}
}