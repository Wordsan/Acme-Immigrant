package services;

import java.util.Map;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

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
	OfficerService officerService;

	@Autowired
	ApplicationService applicationService;

	// Tests ------------------------------------------------------------------

	/*
	 * RF 11.2 An actor who is authenticated must be able to edit his or her
	 * user account data
	 */
	@Test
	public void driver() {
		final Object testingData[][] = {
				{ "officer1", "Chicote", null },
				{ "officer2", "Chicote",
						new ForbbidenActionException().getClass() },
				{ "immigrant1", "Chicote",
						new ObjectNotFoundException().getClass() } };

		for (int i = 0; i < testingData.length; i++)
			this.template((String) testingData[i][0],
					(String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void template(final String beanName, final String name,
			final Class<?> expected) {
		Class<?> caught;
		Officer a;

		caught = null;
		try {
			super.authenticate("officer1");
			final int id = super.getEntityId(beanName);
			a = this.officerService.findOne(id);
			a.setName(name);
			this.officerService.save(a);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * RF 14.1 An actor who is authenticated as an administrator must be able to
	 * create user accounts for new officers and investigators
	 */

	@Test
	public void driverRegister() {
		final Object testingData[][] = { { "admin", null },
				{ "officer1", ForbbidenActionException.class },
				{ "immigrant1", ForbbidenActionException.class } };

		for (int i = 0; i < testingData.length; i++)
			this.registerTemplate((String) testingData[i][0],
					(Class<?>) testingData[i][1]);
	}

	protected void registerTemplate(final String user, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			if (user != null)
				super.authenticate(user);
			else
				super.unauthenticate();
			final Officer i = this.officerService.create();
			i.setName("pepe");
			i.setSurname("shurmano");
			i.setEmail("email@user.com");
			i.getUserAccount().setPassword("prueba");
			i.getUserAccount().setUsername("userPrueba");
			this.officerService.save(i);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	// RF 14.3.2 An actor who is authenticated as an administrator must be able
	// to display a dash board with some statistics about applications per
	// officer
	@Test
	public void driverStatistics() {
		final Object testingData[][] = { { "admin", "AVG", null },
				{ "officer1", "AVG", ForbbidenActionException.class },
				{ "admin", "LMAO", IllegalArgumentException.class } };

		for (int i = 0; i < testingData.length; i++)
			this.statisticsTemplate((String) testingData[i][0],
					(String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void statisticsTemplate(final String user, final String stat,
			final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(user);
			final Map<String, Double> statistics = this.officerService
					.applicationsStadistics();
			Assert.isTrue(statistics.get(stat) != null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
