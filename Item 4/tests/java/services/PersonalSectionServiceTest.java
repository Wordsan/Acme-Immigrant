package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import utilities.ForbbidenActionException;
import utilities.RandomUtilities;
import domain.PersonalSection;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class PersonalSectionServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------
	@Autowired
	PersonalSectionService personalSectionService;

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
			final PersonalSection c = this.personalSectionService.create();
			c.setBirthDate(RandomUtilities.generarFechaPasada());
			c.setBirthPlace(RandomUtilities.generarTexto());
			c.setFullNames(RandomUtilities.generarTexto());
			c.setPicture(RandomUtilities.generarURL());
			this.personalSectionService.save(c);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
