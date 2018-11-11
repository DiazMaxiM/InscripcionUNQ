package inscripcionunq;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ar.edu.unq.inscripcionunq.spring.model.Carrera;
import ar.edu.unq.inscripcionunq.spring.model.Materia;
import ar.edu.unq.inscripcionunq.spring.model.TipoEstado;

public class MateriaTest {
	
	private Materia materia;
	private List<Carrera> carreras;
	
	@Before
	public void setUp() throws Exception {
		carreras = new ArrayList<Carrera>();
		materia = new Materia("10", "TIP", 8, carreras);
	}
	
	@Test
	public void crearMateriaConCodigo10NombreTIPCantHorasYVerificarCodigoNombreYEstado(){
		Materia materiaB = new Materia("10", "TIP", 8);
		
		assertEquals("10", materiaB.getCodigo());
		assertEquals("TIP", materiaB.getNombre());
		assertEquals(new Integer(8), materiaB.getHoras());
		assertEquals(TipoEstado.ENABLED, materiaB.getEstado());
	}

	@Test
	public void crearMateriaConCodigo10NombreTIPySinCarrerasVerificarCodigoNombreEstadoYCarreras() {
		assertEquals("10", materia.getCodigo());
		assertEquals("TIP", materia.getNombre());
		assertEquals(new Integer(8), materia.getHoras());
		assertEquals(TipoEstado.ENABLED, materia.getEstado());
		assertEquals(0, materia.getCarreras().size());
	}

	@Test
	public void crearMateriaConCodigo10NombreTIPyUnaCarreraYVerificarCodigoNombreEstadoYCarreraAgregada() {
		Carrera career = Mockito.mock(Carrera.class);
		carreras.add(career);
		
		assertEquals(1, materia.getCarreras().size());
		assertEquals(career, materia.getCarreras().get(0));
	}

	@Test
	public void crearMateriaConCodigo10NombreTIPyUnaCarreraLuegoEliminarlaYVerificarQueSeHayaEliminado() {
		Carrera career = Mockito.mock(Carrera.class);
		materia.agregarCarrera(career);
		assertEquals(1, materia.getCarreras().size());
		assertEquals(career, materia.getCarreras().get(0));
		materia.eliminarCarrera(career);
		assertEquals(0, materia.getCarreras().size());
	}

	@Test
	public void dehabilitarMateriaYVerificarElNuevoEstado() {
		materia.deshabilitar();
		
		assertEquals(TipoEstado.DISABLED, materia.getEstado());
	}
}