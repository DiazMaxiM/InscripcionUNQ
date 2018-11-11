package inscripcionunq;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import ar.edu.unq.inscripcionunq.spring.model.Carrera;
import ar.edu.unq.inscripcionunq.spring.model.TipoEstado;

public class CarreraTest {
	
	private Carrera carrera;
	
	@Before
	public void setUp() throws Exception {
		carrera = new Carrera("W", "Licenciatura En Informatica");
	}

	@Test
	public void crearNuevaCarreraConCodigoWyNombreLicenciaturaEnInformaticaYVerificarElNombreYCodigo() {
		assertEquals("W", carrera.getCodigo());
		assertEquals("Licenciatura En Informatica", carrera.getDescripcion());
		assertEquals(TipoEstado.ENABLED, carrera.getEstado());
	}

	@Test
	public void crearNuevaCarreraConCodigoWyNombreLicenciaturaEnInformaticaCambiarElEstadoADeshabilitadoYVerificarNombreCodigoYEstado() {
		carrera.deshabilitar();
		assertEquals("W", carrera.getCodigo());
		assertEquals("Licenciatura En Informatica", carrera.getDescripcion());
		assertEquals(TipoEstado.DISABLED, carrera.getEstado());
	}
}