package inscripcionunq;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;

import ar.edu.unq.inscripcionunq.spring.model.Career;
import ar.edu.unq.inscripcionunq.spring.model.Subject;
import ar.edu.unq.inscripcionunq.spring.model.TypeStatus;

public class MatterTest {

	@Test
	public void newMatterWithCode10AndNameTIPAndWithOutListCareerVerifyCodeAndNameAndStatusAndCountCareerEquals0() {
		List<Career> careers = new ArrayList<Career>();
		Subject matter = new Subject("10", "TIP", 8, careers);
		assertEquals("10", matter.getCode());
		assertEquals("TIP", matter.getName());
		assertEquals(new Integer(8), matter.getHours());
		assertEquals(TypeStatus.ENABLED, matter.getStatus());
		assertEquals(0, matter.getListCareers().size());

	}

	@Test
	public void newMatterWithCode10AndNameTIPAndAddOneCareerVerifyCountCareerEquals1() {
		List<Career> careers = new ArrayList<Career>();
		Career career = Mockito.mock(Career.class);
		// Mockito.when(transaction.isNotRejected()).thenReturn(true);
		careers.add(career);
		Subject matter = new Subject("10", "TIP", 8, careers);
		assertEquals(1, matter.getListCareers().size());
		assertEquals(career, matter.getListCareers().get(0));

	}

	@Test
	public void newMatterWithCode10AndNameTIPAndAddOneCareerVerifyCountCareerEquals1AfterRemoveTheCareerVerifyCountCareerEquals0() {
		Career career = Mockito.mock(Career.class);
		// Mockito.when(transaction.isNotRejected()).thenReturn(true);
		Subject matter = new Subject("10", "TIP", 8);
		matter.addCareer(career);
		assertEquals(1, matter.getListCareers().size());
		assertEquals(career, matter.getListCareers().get(0));
		matter.removeCareer(career);
		assertEquals(0, matter.getListCareers().size());
	}

	@Test
	public void newMatterWithCode10AndNameTIPAndTheDisabledVerifyThatTheStatusIsDisabled() {
		List<Career> careers = new ArrayList<Career>();
		Career career = Mockito.mock(Career.class);
		// Mockito.when(transaction.isNotRejected()).thenReturn(true);
		careers.add(career);
		Subject matter = new Subject("10", "TIP", 8, careers);
		matter.disabled();
		assertEquals(TypeStatus.DISABLED, matter.getStatus());

	}

}
