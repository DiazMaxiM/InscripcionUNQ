package inscripcionunq;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ar.edu.unq.inscripcionunq.spring.model.Carrera;
import ar.edu.unq.inscripcionunq.spring.model.Materia;
import ar.edu.unq.inscripcionunq.spring.model.TypeStatus;

public class SubjectTest {
	private Materia subject;
	private List<Carrera> careers;
	
	@Before
	public void setUp() throws Exception {
		careers = new ArrayList<Carrera>();
		subject = new Materia("10", "TIP", 8, careers);
	}
	
	@Test
	public void createSubjectWithCode10NameTIPAndHoursVerifyCodeAndNameAndStatus(){
		Materia subjectA = new Materia("10", "TIP", 8);
		
		assertEquals("10", subjectA.getCodigo());
		assertEquals("TIP", subjectA.getNombre());
		assertEquals(new Integer(8), subjectA.getHoras());
		assertEquals(TypeStatus.ENABLED, subjectA.getEstado());
	}

	@Test
	public void createSubjectWithCode10NameTIPAndWithOutListCareerVerifyCodeAndNameAndStatusAndCountCareerEquals0() {
		assertEquals("10", subject.getCodigo());
		assertEquals("TIP", subject.getNombre());
		assertEquals(new Integer(8), subject.getHoras());
		assertEquals(TypeStatus.ENABLED, subject.getEstado());
		assertEquals(0, subject.getCarreras().size());
	}

	@Test
	public void newSubjectWithCode10AndNameTIPAndAddOneCareerVerifyCountCareerEquals1() {
		Carrera career = Mockito.mock(Carrera.class);
		careers.add(career);
		
		assertEquals(1, subject.getCarreras().size());
		assertEquals(career, subject.getCarreras().get(0));
	}

	@Test
	public void newSubjectWithCode10AndNameTIPAndAddOneCareerVerifyCountCareerEquals1AfterRemoveTheCareerVerifyCountCareerEquals0() {
		Carrera career = Mockito.mock(Carrera.class);
		subject.agregarCarrera(career);
		assertEquals(1, subject.getCarreras().size());
		assertEquals(career, subject.getCarreras().get(0));
		subject.eliminarCarrera(career);
		assertEquals(0, subject.getCarreras().size());
	}

	@Test
	public void newSubjectWithCode10AndNameTIPAndTheDisabledVerifyThatTheStatusIsDisabled() {
		subject.deshabilitar();
		
		assertEquals(TypeStatus.DISABLED, subject.getEstado());
	}
}