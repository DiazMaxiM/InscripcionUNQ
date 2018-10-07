package inscripcionunq;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import ar.edu.unq.inscripcionunq.spring.model.Carrera;
import ar.edu.unq.inscripcionunq.spring.model.Comision;
import ar.edu.unq.inscripcionunq.spring.model.OfertaAcademica;
import ar.edu.unq.inscripcionunq.spring.model.Periodo;

public class AcademicOfferTest {
	private OfertaAcademica ofertaAcademica;
	
	@Before
	public void setUp() throws Exception {
		Carrera carrera = Mockito.mock(Carrera.class);
		Periodo periodo = Mockito.mock(Periodo.class);
		ofertaAcademica = new OfertaAcademica("OA-P-S2-18", "Oferta Academica TPI 2 semestre 2018", carrera,periodo);
	}
	
	@Test
	public void crearUnaOfertaYverificarQueNoTieneComisiones() {		
		assertEquals(0, ofertaAcademica.getComisiones().size());
	}

	@Test
	public void agregarComisionYVerificarQueFueAgregada() {
		Comision comision = Mockito.mock(Comision.class);
		ofertaAcademica.agregarComision(comision);
		
		assertTrue(ofertaAcademica.getComisiones().contains(comision));
		assertEquals(1, ofertaAcademica.getComisiones().size());
	}
	
	@Test
	public void eliminoComisionyVerificoSiFueEliminada() {
		Comision comision1 = Mockito.mock(Comision.class);
		Comision comision2 = Mockito.mock(Comision.class);
		ofertaAcademica.agregarComision(comision1);
		ofertaAcademica.agregarComision(comision2);
		
		assertTrue(ofertaAcademica.getComisiones().contains(comision1));
		
		ofertaAcademica.eliminarComision(comision1);
		assertFalse(ofertaAcademica.getComisiones().contains(comision1));
	}
}
