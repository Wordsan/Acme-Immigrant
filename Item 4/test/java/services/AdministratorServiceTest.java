package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import utilities.ForbbidenActionException;
import utilities.ObjectNotFoundException;
import domain.Administrator;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AdministratorServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------
	@Autowired
	AdministratorService administratorService;

	// Tests ------------------------------------------------------------------

	/*
	 * RF 11.2 An actor who is authenticated must be able to edit his or her
	 * user account data
	 */
	@Test
	public void driver() {
		final Object testingData[][] = {
				{ "administrator1", "Chicote", null },
				{ "administrator2", "Chicote", ForbbidenActionException.class },
				{ "immigrant1", "Chicote", ObjectNotFoundException.class } };

		for (int i = 0; i < testingData.length; i++)
			this.template((String) testingData[i][0],
					(String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void template(final String beanName, final String name,
			final Class<?> expected) {
		Class<?> caught;
		Administrator a;

		caught = null;
		try {
			super.authenticate("admin");
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
