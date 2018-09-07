package inscripcionunq;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ar.edu.unq.inscripcionunq.spring.model.Career;
import ar.edu.unq.inscripcionunq.spring.model.Subject;
import ar.edu.unq.inscripcionunq.spring.model.TypeStatus;

public class SubjectTest {
	private Subject subject;
	private List<Career> careers;
	
	@Before
	public void setUp() throws Exception {
		careers = new ArrayList<Career>();
		subject = new Subject("10", "TIP", 8, careers);
	}
	
	@Test
	public void createSubjectWithCode10NameTIPAndHoursVerifyCodeAndNameAndStatus(){
		Subject subjectA = new Subject("10", "TIP", 8);
		
		assertEquals("10", subjectA.getCode());
		assertEquals("TIP", subjectA.getName());
		assertEquals(new Integer(8), subjectA.getHours());
		assertEquals(TypeStatus.ENABLED, subjectA.getStatus());
	}

	@Test
	public void createSubjectWithCode10NameTIPAndWithOutListCareerVerifyCodeAndNameAndStatusAndCountCareerEquals0() {
		assertEquals("10", subject.getCode());
		assertEquals("TIP", subject.getName());
		assertEquals(new Integer(8), subject.getHours());
		assertEquals(TypeStatus.ENABLED, subject.getStatus());
		assertEquals(0, subject.getListCareers().size());
	}

	@Test
	public void newSubjectWithCode10AndNameTIPAndAddOneCareerVerifyCountCareerEquals1() {
		Career career = Mockito.mock(Career.class);
		careers.add(career);
		
		assertEquals(1, subject.getListCareers().size());
		assertEquals(career, subject.getListCareers().get(0));
	}

	@Test
	public void newSubjectWithCode10AndNameTIPAndAddOneCareerVerifyCountCareerEquals1AfterRemoveTheCareerVerifyCountCareerEquals0() {
		Career career = Mockito.mock(Career.class);
		subject.addCareer(career);
		assertEquals(1, subject.getListCareers().size());
		assertEquals(career, subject.getListCareers().get(0));
		subject.removeCareer(career);
		assertEquals(0, subject.getListCareers().size());
	}

	@Test
	public void newSubjectWithCode10AndNameTIPAndTheDisabledVerifyThatTheStatusIsDisabled() {
		subject.disabled();
		
		assertEquals(TypeStatus.DISABLED, subject.getStatus());
	}
}