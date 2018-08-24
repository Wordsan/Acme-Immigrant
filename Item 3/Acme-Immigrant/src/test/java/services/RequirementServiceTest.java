package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import utilities.ForbbidenActionException;
import domain.Requirement;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class RequirementServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------
	@Autowired
	RequirementService requirementService;

	@Autowired
	LawService lawService;

	// Tests ------------------------------------------------------------------

	@Test
	public void driver() {
		final Object testingData[][] = { { "admin1", 0, null },
				{ "officer1", 0, ForbbidenActionException.class },
				{ "admin1", 1, null },
				{ "officer1", 1, ForbbidenActionException.class },
				{ "officer1", 2, ForbbidenActionException.class },
				{ "admin1", 2, null } };

		for (int i = 0; i < testingData.length; i++)
			this.template((String) testingData[i][0], (int) testingData[i][1],
					(Class<?>) testingData[i][2]);
	}

	// Ancillary methods ------------------------------------------------------

	protected void template(final String user, final int operacion,
			final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(user);
			if (operacion == 0) {
				final Requirement r = this.requirementService.create();
				r.setDescription("description");
				r.setLaw(this.lawService.findOne(super.getEntityId("law1")));
				r.setTitle("title");
				this.requirementService.save(r);
			} else if (operacion == 1) {
				final Requirement r = this.requirementService.findOne(super
						.getEntityId("requirement1"));
				r.setTitle("titulo");
				this.requirementService.save(r);
			} else {
				final Requirement r = this.requirementService.findOne(super
						.getEntityId("requirement1"));
				this.requirementService.delete(r);
			}
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
