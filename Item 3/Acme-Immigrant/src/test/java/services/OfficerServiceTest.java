package services;

import java.lang.instrument.IllegalClassFormatException;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import utilities.ForbbidenActionException;
import utilities.ObjectNotFoundException;
import domain.Officer;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class OfficerServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------
	@Autowired
	OfficerService administratorService;

	@Autowired
	ApplicationService officerService;

	// Tests ------------------------------------------------------------------

	@Test
	public void driver() {
		final Object testingData[][] = {
				{ "officer1", "Chicote", null },
				{ "officer2", "Chicote",
						new ForbbidenActionException().getClass() } };

		for (int i = 0; i < testingData.length; i++)
			this.template((String) testingData[i][0],
					(String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	@Test
	public void assign() throws ForbbidenActionException,
			ObjectNotFoundException, IllegalClassFormatException {
		super.authenticate("immigrant1");
		this.officerService.close(super.getEntityId("application1"));
		super.authenticate("officer1");
		this.officerService.assign(super.getEntityId("application1"));
	}

	// Ancillary methods ------------------------------------------------------

	protected void template(final String beanName, final String name,
			final Class<?> expected) {
		Class<?> caught;
		Officer a;

		caught = null;
		try {
			super.authenticate("officer1");
			final int id = super.getEntityId(beanName);
			a = this.administratorService.findOne(id);
			a.setName(name);
			this.administratorService.save(a);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
