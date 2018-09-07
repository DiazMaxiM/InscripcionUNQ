package inscripcionunq;

import static org.junit.Assert.*;
import java.time.LocalTime;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ar.edu.unq.inscripcionunq.spring.model.Commission;
import ar.edu.unq.inscripcionunq.spring.model.Subject;
import ar.edu.unq.inscripcionunq.spring.model.TypeDay;
import ar.edu.unq.inscripcionunq.spring.model.TypeStatus;

public class CommissionTest {
	private Commission commission;
	private Subject subject;

	@Before
	public void setUp() throws Exception {
		subject = Mockito.mock(Subject.class);
		commission = new Commission("Mate1 C1", subject, 30);
	}

	@Test
	public void createACommissionAndVerifyNameSubjectAndStatus() {
		assertEquals("Mate1 C1", commission.getName());
		assertEquals(subject, commission.getSubject());
		assertEquals(TypeStatus.ENABLED, commission.getStatus());
	}
	
	@Test
	public void addCommissionTimeAndVerifyIfTheIntervalIsCreated() {
		commission.addHours(TypeDay.MARTES, LocalTime.of(9, 00), 4.0f);

		assertEquals(1, commission.getIntervals().size());
		assertEquals(TypeDay.MARTES, commission.getIntervals().get(0).getDay());
		assertEquals(LocalTime.of(9, 00), commission.getIntervals().get(0).getStart());
		assertEquals(4.0, commission.getIntervals().get(0).getCountHours(), 0);
	}
	
	@Test
	public void disabledCommissionAndVerifyNewStatus() {
		commission.disabled();
		
		assertEquals(TypeStatus.DISABLED, commission.getStatus());
	}
}