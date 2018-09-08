package inscripcionunq;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ar.edu.unq.inscripcionunq.spring.model.Career;
import ar.edu.unq.inscripcionunq.spring.model.Commission;
import ar.edu.unq.inscripcionunq.spring.model.Poll;
import ar.edu.unq.inscripcionunq.spring.model.Student;
import ar.edu.unq.inscripcionunq.spring.model.Subject;

public class StudentTest {
	private Student student;

	@Before
	public void setUp() throws Exception {
		student = new Student("Ana", "Martínez", "40111999", "ana.m@gmail.com");
	}

	@Test
	public void createStudentAndVerifyNameLastnameDniAndEmail() {
		assertEquals("Ana", student.getName());
		assertEquals("Martínez", student.getLastName());
		assertEquals("40111999", student.getDni());
		assertEquals("ana.m@gmail.com", student.getMail());
	}
	
	@Test
	public void createStudentAndVerifyRegularity() {
		assertTrue(student.getRegularity());
	}
	
	@Test
	public void addApprovedSubjectAndVerifyIfItWasAddedAndApproval() {
		Subject subject = Mockito.mock(Subject.class);
		student.addMatterAprroved(subject);
		
		assertTrue(student.isApproved(subject));
		assertTrue(student.getSubjectsApproved().contains(subject));
	}
	
	@Test
	public void addCareerAndVerifyIfItWasAddedInCareers() {
		Career career = Mockito.mock(Career.class);
		student.addcareerInscription(career);
		
		assertTrue(student.getCareersInscription().contains(career));
	}
	
	@Test
	public void updateStudentAndVerifyIfItWasChange() {
		Student studentCurr = new Student("Ana", "Martínez", "40111999", "ana.mh@gmail.com");
		assertTrue(student.isChange(studentCurr));
		student.update(studentCurr);
		assertFalse(student.isChange(studentCurr));
	}
	
	@Test
	public void addCommissionRegistrationAndVerifyIfItAdded() {
		Commission commission = Mockito.mock(Commission.class);
		student.addCommissionRegistration(commission);
		
		assertTrue(student.getCommissionsRegistration().contains(commission));
	}
	
	@Test
	public void addPollAndVerifyIfItAdded() {
		Poll poll = Mockito.mock(Poll.class);
		student.setPoll(poll);
		
		assertEquals(poll, student.getPoll());
	}
}