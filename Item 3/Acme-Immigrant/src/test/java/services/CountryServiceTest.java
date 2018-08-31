package services;

import java.util.Collection;
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
import utilities.RandomUtilities;
import domain.Country;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CountryServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------
	@Autowired
	CountryService countryService;

	@Autowired
	VisaService visaService;

	// Tests ------------------------------------------------------------------

	// RF 26.1 An actor authenticated as an administrator must be able to create
	// countries
	@Test
	public void driver() {
		final Object testingData[][] = { { "admin", null },
				{ "officer1", ForbbidenActionException.class },
				{ "immigrant1", ForbbidenActionException.class } };

		for (int i = 0; i < testingData.length; i++)
			this.template((String) testingData[i][0],
					(Class<?>) testingData[i][1]);
	}

	// RF 26.1 An actor authenticated as an administrator must be able to edit
	// countries
	@Test
	public void driverEdit() {
		final Object testingData[][] = {
				{ "country1", "admin", "pais", null },
				{ "country2", "immigrant1", "pais",
						ForbbidenActionException.class },
				{ "country15", "admin2", "pais", NumberFormatException.class } };

		for (int i = 0; i < testingData.length; i++)
			this.templateEdit((String) testingData[i][0],
					(String) testingData[i][1], (String) testingData[i][2],
					(Class<?>) testingData[i][3]);
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

	// RF 26.1 An actor authenticated as an administrator must be able to delete
	// countries
	@Test
	public void driverDelete() {
		final Object testingData[][] = { { "country1", "admin", null },
				{ "country2", "immigrant1", ForbbidenActionException.class },
				{ "country15", "admin2", NumberFormatException.class } };

		for (int i = 0; i < testingData.length; i++)
			this.templateDelete((String) testingData[i][0],
					(String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	// RF 25.1 An actor who is not authenticated must be able to navigate the
	// list of countries and display the visas that they offer
	@Test
	public void driverListDisplay() throws ObjectNotFoundException {
		final Object testingData[][] = {
				{ "admin", "country1", "visa1", null },
				{ null, "country2", "visa2", null },
				{ "immigrant1", "country15", "visa2",
						NumberFormatException.class },
				{ "officer1", "country1", "visa2",
						IllegalArgumentException.class } };

		for (int i = 0; i < testingData.length; i++)
			this.templateListDisplay((String) testingData[i][0],
					(String) testingData[i][1], (String) testingData[i][2],
					(Class<?>) testingData[i][3]);
	}

	protected void templateListDisplay(final String user, final String country,
			final String visa, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			if (user != null)
				super.authenticate(user);
			final Collection<Country> countries = this.countryService.findAll();
			final Country c = this.countryService.findOne(super
					.getEntityId(country));
			Assert.isTrue(countries.contains(c));
			Assert.isTrue(c.getVisas().contains(
					this.visaService.findOne(super.getEntityId(visa))));
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	// RF 26.5.1 An actor who is authenticated as an administrator must be able
	// to display a dash board with some statistics about laws per country
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
			final Map<String, Double> statistics = this.countryService
					.lawStadistics();
			Assert.isTrue(statistics.get(stat) != null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
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
