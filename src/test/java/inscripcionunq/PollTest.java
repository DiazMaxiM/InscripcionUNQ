package inscripcionunq;

import static org.junit.Assert.*;
import java.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ar.edu.unq.inscripcionunq.spring.model.AcademicOffer;
import ar.edu.unq.inscripcionunq.spring.model.Poll;
import ar.edu.unq.inscripcionunq.spring.model.Student;
import ar.edu.unq.inscripcionunq.spring.model.TypeStatus;

public class PollTest {
	private Poll poll;
	private LocalDateTime start;
	private LocalDateTime end;

	@Before
	public void setUp() throws Exception {
		start = start.of(2018, 8, 25, 00, 00);
		end = end.of(2018, 12, 1, 00, 00);
		poll = new Poll("Encuesta segundo semestre 2018", start, end);
	}

	@Test
	public void createAPollAndVerifyNameStartDateEndDateAndStatus() {
		assertEquals("Encuesta segundo semestre 2018", poll.getName());
		assertEquals(start, poll.getStartDate());;
		assertEquals(end, poll.endDate());
		assertEquals(TypeStatus.ENABLED, poll.getStatus());
	}
	
	@Test
	public void disabledPollAndVerifyNewStatus() {
		poll.disabled();
		
		assertEquals(TypeStatus.DISABLED, poll.getStatus());
	}
	
	@Test
	public void addAcademicOfferAndVerifyIfItWasAddedInAcademicOffers() {
		AcademicOffer academicOffer = Mockito.mock(AcademicOffer.class);
		poll.addAcademicOffer(academicOffer);
		
		assertTrue(poll.getAcademicOffers().contains(academicOffer));
	}
	
	@Test
	public void addStudentInPollAndVerifyIfItWasAddedInStudents() {
		Student student = Mockito.mock(Student.class);
		poll.addStudent(student);
		
		assertTrue(poll.getStudents().contains(student));
	}
}