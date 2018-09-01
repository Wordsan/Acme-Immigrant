package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import utilities.ForbbidenActionException;
import domain.Application;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class DecisionServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------
	@Autowired
	DecisionService decisionService;

	@Autowired
	ApplicationService applicationService;

	// Tests ------------------------------------------------------------------

	// RF 13.4 An actor who is authenticated as an officer must be able to make
	// a decision on an application that he or she's self-assigned. An
	// application that is rejected must include a comment that justifies the
	// rejection
	@Test
	public void driver() {
		final Object testingData[][] = {
				{ "officer3", "application1", false, "forbbiden",
						ForbbidenActionException.class },
				{ "officer2", "application3", false, "forbbiden",
						ForbbidenActionException.class },
				{ "officer3", "application4", false, "",
						IllegalArgumentException.class },
				{ "officer3", "application4", true, "", null },
				{ "officer3", "application4", false, "forbbiden", null } };

		for (int i = 0; i < testingData.length; i++)
			this.template((String) testingData[i][0],
					(String) testingData[i][1], (boolean) testingData[i][2],
					(String) testingData[i][3], (Class<?>) testingData[i][4]);
	}

	// Ancillary methods ------------------------------------------------------

	protected void template(final String user, final String application,
			final boolean accepted, final String reasons,
			final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.authenticate(user);
			final Application a = this.applicationService.findOne(super
					.getEntityId(application));
			this.decisionService.createNew(a.getId(), accepted, reasons);
			this.decisionService.delete(a.getDecision());
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
