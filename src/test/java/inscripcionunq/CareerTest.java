package inscripcionunq;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import ar.edu.unq.inscripcionunq.spring.model.Career;
import ar.edu.unq.inscripcionunq.spring.model.TypeStatus;

public class CareerTest {
	private Career career;
	
	@Before
	public void setUp() throws Exception {
		career = new Career("W", "Licenciatura En Informatica");
	}

	@Test
	public void newCareerWithCodeWAndNameLicenciaturaEnInformaticaVerifyNameAndCode() {
		assertEquals("W", career.getCode());
		assertEquals("Licenciatura En Informatica", career.getDescription());
		assertEquals(TypeStatus.ENABLED, career.getStatus());
	}

	@Test
	public void newCareerWithCodeWAndNameLicenciaturaEnInformaticaChangeStatusToDisabledVerifyNameAndCodeAndStatus() {
		career.disabled();
		assertEquals("W", career.getCode());
		assertEquals("Licenciatura En Informatica", career.getDescription());
		assertEquals(TypeStatus.DISABLED, career.getStatus());
	}
}