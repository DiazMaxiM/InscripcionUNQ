package inscripcionunq;

import static org.junit.Assert.*;
import java.time.LocalTime;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import ar.edu.unq.inscripcionunq.spring.model.Comision;
import ar.edu.unq.inscripcionunq.spring.model.Materia;
import ar.edu.unq.inscripcionunq.spring.model.Periodo;
import ar.edu.unq.inscripcionunq.spring.model.TypeDay;
import ar.edu.unq.inscripcionunq.spring.model.TypeStatus;

public class CommissionTest {
	private Comision commission;
	private Materia subject;

	@Before
	public void setUp() throws Exception {
		subject = Mockito.mock(Materia.class);
		Periodo periodo = Mockito.mock(Periodo.class);
		commission = new Comision("Mate1 C1", subject, 30,periodo);
	}

	@Test
	public void createACommissionAndVerifyNameSubjectAndStatus() {
		assertEquals("Mate1 C1", commission.getNombre());
		assertEquals(subject, commission.getMateria());
		assertEquals(TypeStatus.ENABLED, commission.getEstado());
	}
	
	@Test
	public void addCommissionTimeAndVerifyIfTheIntervalIsCreated() {
		commission.agregarHorarios(TypeDay.MARTES, LocalTime.of(9, 00), 4.0f);

		assertEquals(1, commission.getHorarios().size());
		assertEquals(TypeDay.MARTES, commission.getHorarios().get(0).getDia());
		assertEquals(LocalTime.of(9, 00), commission.getHorarios().get(0).getHoraComienzo());
		assertEquals(4.0, commission.getHorarios().get(0).getCantidadDeHoras(), 0);
	}
	
	@Test
	public void disabledCommissionAndVerifyNewStatus() {
		commission.dehabilitar();
		
		assertEquals(TypeStatus.DISABLED, commission.getEstado());
	}
}