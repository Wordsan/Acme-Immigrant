package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import utilities.ForbbidenActionException;
import domain.EducationSection;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class EducationSectionServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------
	@Autowired
	EducationSectionService educationSectionService;

	// Tests ------------------------------------------------------------------

	@Test
	public void driver() {
		final Object testingData[][] = { { "immigrant1", null },
				{ "officer1", ForbbidenActionException.class } };

		for (int i = 0; i < testingData.length; i++)
			this.template((String) testingData[i][0],
					(Class<?>) testingData[i][1]);
	}

	// Ancillary methods ------------------------------------------------------

	protected void template(final String user, final Class<?> expected) {
		Class<?> caught = null;
		super.authenticate(user);
		try {
			final EducationSection c = this.educationSectionService.create();
			c.setDegreeName("grado");
			c.setInstitution("US");
			c.setLevel("NONE");
			this.educationSectionService.save(c);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
