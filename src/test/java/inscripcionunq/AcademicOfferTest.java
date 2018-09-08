package inscripcionunq;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ar.edu.unq.inscripcionunq.spring.model.AcademicOffer;
import ar.edu.unq.inscripcionunq.spring.model.Career;
import ar.edu.unq.inscripcionunq.spring.model.Commission;

public class AcademicOfferTest {
	private AcademicOffer academicOffer;
	
	@Before
	public void setUp() throws Exception {
		Career career = Mockito.mock(Career.class);
		academicOffer = new AcademicOffer("OA-P-S2-18", "Oferta Academica TPI 2 semestre 2018", career);
	}
	
	@Test
	public void createAnOfferAndVerifyThisHasNoCommissions() {		
		assertEquals(0, academicOffer.getCommissions().size());
	}

	@Test
	public void addACommissionAndVerifyThatItWasAdded() {
		Commission commission = Mockito.mock(Commission.class);
		academicOffer.addCommission(commission);
		
		assertTrue(academicOffer.getCommissions().contains(commission));
		assertEquals(1, academicOffer.getCommissions().size());
	}
}