package inscripcionunq;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import ar.edu.unq.inscripcionunq.spring.model.Carrera;
import ar.edu.unq.inscripcionunq.spring.model.Comision;
import ar.edu.unq.inscripcionunq.spring.model.OfertaAcademica;

public class AcademicOfferTest {
	private OfertaAcademica academicOffer;
	
	@Before
	public void setUp() throws Exception {
		Carrera career = Mockito.mock(Carrera.class);
		academicOffer = new OfertaAcademica("OA-P-S2-18", "Oferta Academica TPI 2 semestre 2018", career);
	}
	
	@Test
	public void createAnOfferAndVerifyThisHasNoCommissions() {		
		assertEquals(0, academicOffer.getCommissions().size());
	}

	@Test
	public void addACommissionAndVerifyThatItWasAdded() {
		Comision commission = Mockito.mock(Comision.class);
		academicOffer.agregarComision(commission);
		
		assertTrue(academicOffer.getCommissions().contains(commission));
		assertEquals(1, academicOffer.getCommissions().size());
	}
}