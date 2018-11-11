package inscripcionunq;

import static org.junit.Assert.*;
import java.time.LocalTime;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import ar.edu.unq.inscripcionunq.spring.model.Comision;
import ar.edu.unq.inscripcionunq.spring.model.Materia;
import ar.edu.unq.inscripcionunq.spring.model.Periodo;
import ar.edu.unq.inscripcionunq.spring.model.TipoDia;
import ar.edu.unq.inscripcionunq.spring.model.TipoEstado;

public class ComisionTest {
	
	private Comision comision;
	private Materia materia;

	@Before
	public void setUp() throws Exception {
		materia = Mockito.mock(Materia.class);
		Periodo periodo = Mockito.mock(Periodo.class);
		comision = new Comision("Mate1 C1", materia, 30,periodo);
	}

	@Test
	public void crearUnaComisionYVerificarNombreDeMateriaYEstado() {
		assertEquals("Mate1 C1", comision.getNombre());
		assertEquals(materia, comision.getMateria());
		assertEquals(TipoEstado.ENABLED, comision.getEstado());
	}
	
	@Test
	public void agregarHorarioDeComisionYVerificarSiFueCreadoElIntervalo() {
		comision.agregarHorarios(TipoDia.MARTES, LocalTime.of(9, 00), 4.0f);

		assertEquals(1, comision.getHorarios().size());
		assertEquals(TipoDia.MARTES, comision.getHorarios().get(0).getDia());
		assertEquals(LocalTime.of(9, 00), comision.getHorarios().get(0).getHoraComienzo());
		assertEquals(4.0, comision.getHorarios().get(0).getCantidadDeHoras(), 0);
	}
	
	@Test
	public void deshabilitarComisionYVerificarElNuevoEstado() {
		comision.deshabilitar();
		
		assertEquals(TipoEstado.DISABLED, comision.getEstado());
	}
}