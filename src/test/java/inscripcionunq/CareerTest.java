package inscripcionunq;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import ar.edu.unq.inscripcionunq.spring.model.Carrera;
import ar.edu.unq.inscripcionunq.spring.model.TypeStatus;

public class CareerTest {
	private Carrera career;
	
	@Before
	public void setUp() throws Exception {
		career = new Carrera("W", "Licenciatura En Informatica");
	}

	@Test
	public void newCareerWithCodeWAndNameLicenciaturaEnInformaticaVerifyNameAndCode() {
		assertEquals("W", career.getCodigo());
		assertEquals("Licenciatura En Informatica", career.getDescripcion());
		assertEquals(TypeStatus.ENABLED, career.getEstado());
	}

	@Test
	public void newCareerWithCodeWAndNameLicenciaturaEnInformaticaChangeStatusToDisabledVerifyNameAndCodeAndStatus() {
		career.deshabilitar();
		assertEquals("W", career.getCodigo());
		assertEquals("Licenciatura En Informatica", career.getDescripcion());
		assertEquals(TypeStatus.DISABLED, career.getEstado());
	}
}