package inscripcionunq;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import ar.edu.unq.inscripcionunq.spring.model.Career;
import ar.edu.unq.inscripcionunq.spring.model.TypeStatus;

public class CareerTest {

	@Test
	public void newCareerWithCodeWAndNameLicenciaturaEnInformaticaVerifyNameAndCode() {
		Career career = new Career("W", "Licenciatura En Informatica");
		assertEquals("W", career.getCode());
		assertEquals("Licenciatura En Informatica", career.getDescription());
		assertEquals(TypeStatus.ENABLED, career.getStatus());
	}

	@Test
	public void newCareerWithCodeWAndNameLicenciaturaEnInformaticaChangeStatusToDisabledVerifyNameAndCodeAndStatus() {
		Career career = new Career("W", "Licenciatura En Informatica");
		assertEquals(TypeStatus.ENABLED, career.getStatus());
		assertEquals("W", career.getCode());
		assertEquals("Licenciatura En Informatica", career.getDescription());
		career.disabled();
		assertEquals("W", career.getCode());
		assertEquals("Licenciatura En Informatica", career.getDescription());
		assertEquals(TypeStatus.DISABLED, career.getStatus());

	}

}
