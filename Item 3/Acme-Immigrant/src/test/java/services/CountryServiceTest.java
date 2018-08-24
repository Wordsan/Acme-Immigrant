package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import utilities.ForbbidenActionException;
import utilities.RandomUtilities;
import domain.Country;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CountryServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------
	@Autowired
	CountryService countryService;

	// Tests ------------------------------------------------------------------

	@Test
	public void driver() {
		final Object testingData[][] = { { "admin1", null },
				{ "officer1", ForbbidenActionException.class } };

		for (int i = 0; i < testingData.length; i++)
			this.template((String) testingData[i][0],
					(Class<?>) testingData[i][1]);
	}

	@Test
	public void driverEdit() {
		final Object testingData[][] = {
				{ "country1", "admin1", "pais", null },
				{ "country2", "immigrant1", "pais",
						ForbbidenActionException.class } };

		for (int i = 0; i < testingData.length; i++)
			this.templateEdit((String) testingData[i][0],
					(String) testingData[i][1], (String) testingData[i][2],
					(Class<?>) testingData[i][3]);
	}

	@Test
	public void driverDelete() {
		final Object testingData[][] = { { "country1", "admin1", null },
				{ "country2", "immigrant1", ForbbidenActionException.class } };

		for (int i = 0; i < testingData.length; i++)
			this.templateDelete((String) testingData[i][0],
					(String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	// Ancillary methods ------------------------------------------------------

	protected void template(final String user, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(user);
			final Country c = this.countryService.create();
			c.setName("pais");
			c.setISOCode("324");
			c.setFlag(RandomUtilities.generarURL());
			c.setWikiPage(RandomUtilities.generarURL());
			this.countryService.save(c);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void templateEdit(final String country, final String user,
			final String name, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(user);
			final Country c = this.countryService.findOne(super
					.getEntityId(country));
			c.setName(name);
			this.countryService.save(c);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	private void templateDelete(final String country, final String user,
			final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(user);
			final Country c = this.countryService.findOne(super
					.getEntityId(country));
			this.countryService.delete(c);
			Assert.isTrue(!this.countryService.findAll().contains(c));
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

}
