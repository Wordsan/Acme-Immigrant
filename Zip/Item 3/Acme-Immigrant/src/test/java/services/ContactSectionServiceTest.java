package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import utilities.ForbbidenActionException;
import domain.ContactSection;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ContactSectionServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------
	@Autowired
	ContactSectionService contactSectionService;

	// Tests ------------------------------------------------------------------

	@Test
	public void driver() {
		final Object testingData[][] = {
				{ "immigrant1", "user@user.com", null },
				{ "officer1", "user@user.com", ForbbidenActionException.class } };

		for (int i = 0; i < testingData.length; i++)
			this.template((String) testingData[i][0],
					(String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	// Ancillary methods ------------------------------------------------------

	protected void template(final String user, final String mail,
			final Class<?> expected) {
		Class<?> caught = null;
		super.authenticate(user);
		try {
			final ContactSection c = this.contactSectionService.create();
			c.setEmailAddress(mail);
			this.contactSectionService.save(c);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
